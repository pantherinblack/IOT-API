package ch.ksh.iotapi.handler

import ch.ksh.iotapi.util.ConfigReader
import java.sql.*
import java.time.LocalDateTime

/**
 * Business logic for all records
 * @author Kevin Stupar
 * @since 07.05.2023
 */
class SQLHandler {
    companion object {
        private var connection: Connection? = null
        private var rs: ResultSet? = null

        /**
         * creates and returns a connection object
         *
         * @return Connection
         */
        @Throws(SQLException::class)
        fun getConnection(): Connection {
            if (connection == null || connection!!.isClosed || !connection!!.isValid(2)) {
                connection = DriverManager.getConnection(
                    ConfigReader.readConfig("jdbcURL"),
                    ConfigReader.readConfig("dbUser"),
                    ConfigReader.readConfig("dbPassword")
                )
                getConnection()
            }
            return connection!!
        }

        /**
         * creates and returns a prepared statement
         *
         * @param sql to be executed
         * @return PreparedStatement
         */
        @Throws(SQLException::class)
        private fun getPreparedStatement(sql: String): PreparedStatement {
            return getConnection().prepareStatement(sql)
        }

        /**
         * return the resultSet of the query
         *
         * @param sql to be executed
         * @return ResultSet
         */
        fun getResultSet(sql: String): ResultSet {
            return getResultSet(sql, null)
        }

        /**
         * fills and returns a preparedStatement
         *
         * @param sql to be executed
         * @param elements to be added (Key -> 0-i, Value -> Object)
         * @return PreparedStatement
         */
        fun getFilledPreparedStatement(sql: String, elements: Map<Int, Any?>?): PreparedStatement {
            if (getConnection().isValid(2)) {
                try {
                    val ps: PreparedStatement = getPreparedStatement(sql)
                    if (elements != null) {
                        for (i in 1..elements.size) {
                            val float: Float = Float.MIN_VALUE
                            val aClass: Class<*> = elements[i]!!.javaClass
                            val floatClass: Class<*> = float.javaClass
                            if (Int::javaClass == aClass) {
                                ps.setInt(i, elements[i] as Int)
                            } else if (Timestamp.valueOf(LocalDateTime.now()).javaClass == aClass) {
                                ps.setTimestamp(i, elements[i] as Timestamp)
                            } else if (floatClass == aClass) {
                                ps.setFloat(i, elements[i] as Float)
                            } else if ("".javaClass == aClass) {
                                ps.setString(i, elements[i] as String)
                            } else {
                                ps.setString(i, elements[i].toString())
                            }
                        }
                    }
                    return ps
                } catch (throwable: SQLException) {
                    throw RuntimeException(throwable)
                }
            }
            throw RuntimeException("Connection Refused!")
        }

        /**
         * return the resultSet of the query
         *
         * @param sql to be executed
         * @return ResultSet
         */
        fun getResultSet(sql: String, elements: Map<Int, Any?>?): ResultSet {
            return getFilledPreparedStatement(sql, elements).executeQuery()
        }

        /**
         * executes a statement
         *
         * @param sql to be executed
         * @param elements to be added (Key -> 0-i, Value -> Object)
         */
        fun executeStatement(sql: String, elements: Map<Int, Any?>?) {
            getFilledPreparedStatement(sql, elements).execute()
            sqlClose()
        }

        /**
         * converts a ResultSet to a list of objects
         *
         * @param rs to be converted
         * @param dataClass the class of objects
         * @return List of Objects (List<dataclass>)
         */
        fun <T> resultSetToArrayList(rs: ResultSet, dataClass: Class<T>): ArrayList<T> {
            val list = ArrayList<T>()
            val constructor = dataClass.getDeclaredConstructor()


            while (rs.next()) {
                val metaData = rs.metaData
                val columnCount = metaData.columnCount

                Array<Any?>(columnCount) { index ->
                    rs.getObject(index + 1)
                }

                val instance = constructor.newInstance()
                val setters = dataClass.methods.filter { it.name.startsWith("set") }

                for (setter in setters) {
                    val paramName = setter.name.substring(3)
                    val paramType = setter.parameterTypes[0]
                    var columnValue = rs.getObject(paramName)

                    if (columnValue::class.java == LocalDateTime.now()::class.java)
                        columnValue = Timestamp.valueOf(columnValue as LocalDateTime)
                    if (columnValue != null && paramType.isAssignableFrom(columnValue::class.java)) {
                        setter.invoke(instance, columnValue)
                    }
                }

                list.add(instance)
            }

            sqlClose()

            return list
        }

        /**
         * closes the connection to the database
         */
        @Throws(SQLException::class)
        fun sqlClose() {
            rs?.close()
            connection!!.close()
        }
    }
}