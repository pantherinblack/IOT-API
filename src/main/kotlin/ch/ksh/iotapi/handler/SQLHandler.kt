package ch.ksh.iotapi.handler

import java.sql.*
import java.time.LocalDateTime
import kotlin.reflect.KClass

class SQLHandler {
    companion object {
        private var connection: Connection = getConnection()
        private var rs: ResultSet? = null

        @Throws(SQLException::class)
        fun getConnection(): Connection {
            if (connection.isClosed || !connection.isValid(2)) {
                connection = DriverManager.getConnection("resource", "user", "pw")
            }
            return connection
        }

        @Throws(SQLException::class)
        private fun getPreparedStatement(sql: String): PreparedStatement {
            return getConnection().prepareStatement(sql)
        }

        fun getResultSet(sql: String): ResultSet? {
            return getResultSet(sql, null)
        }

        fun getResultSet(sql: String, elements: Map<Int, Any>?): ResultSet? {
            return try {
                val ps: PreparedStatement = getPreparedStatement(sql)
                if (elements != null) {
                    for (i in 1..elements.size) {
                        val aClass: Class<*> = elements[i]!!.javaClass
                        if (Int::javaClass == aClass) {
                            ps.setInt(i, elements[i] as Int)
                        } else if (LocalDateTime::javaClass == aClass) {
                            ps.setTimestamp(i, Timestamp.valueOf(elements[i] as LocalDateTime?))
                        } else if (Float::javaClass == aClass) {
                            ps.setFloat(i, elements[i] as Float)
                        } else {
                            ps.setString(i, elements[i].toString())
                        }
                    }
                }
                rs = ps.executeQuery()
                rs
            } catch (throwable: SQLException) {
                throw RuntimeException(throwable)
            }
        }

        fun <T> resultSetToArrayList(rs:ResultSet, dataClass: Class<T>): ArrayList<T> {
            val list = ArrayList<T>()
            val constructor = dataClass.getDeclaredConstructor()

            while (rs.next()) {
                val metaData = rs.metaData
                val columnCount = metaData.columnCount

                val arguments = Array<Any?>(columnCount) { index ->
                    rs.getObject(index + 1)
                }

                val instance = constructor.newInstance()
                val setters = dataClass.methods.filter { it.name.startsWith("set") }

                for (setter in setters) {
                    val paramName = setter.name.substring(3)
                    val paramType = setter.parameterTypes[0]
                    val columnValue = rs.getObject(paramName)

                    if (columnValue != null && paramType.isAssignableFrom(columnValue::class.java)) {
                        setter.invoke(instance, columnValue)
                    }
                }

                list.add(instance)
            }

            return list
        }

        @Throws(SQLException::class)
        fun sqlClose() {
            rs?.close()
            connection.close()
        }
    }
}