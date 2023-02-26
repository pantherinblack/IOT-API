package ch.ksh.iotapi.handler

import java.sql.*
import java.time.LocalDateTime

/**
 * Handles some simple conversions and is able to execute most sql queries needed for this application
 *
 * @author pantherinblack
 * @version 1.3
 * @since 12.08.2022
 */
class SQLHandler {
    private var connection: Connection = getConnection()
    private var rs: ResultSet? = null


    @Throws(SQLException::class)
    fun getConnection(): Connection {
        if (connection.isClosed || !connection.isValid(2)) {
            connection = DriverManager.getConnection("resource", "user", "pw")
        }
        return connection
    }

    /**
     * Returns the PreparedStatement for the sql-query
     *
     * @param sql to be executed
     * @return PreparedStatement
     * @throws SQLException if the connection or the execution fails
     */
    @Throws(SQLException::class)
    private fun getPreparedStatement(sql: String): PreparedStatement {
        return getConnection().prepareStatement(sql)
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

    @Throws(SQLException::class)
    fun sqlClose() {
        rs?.close()
        connection.close()
    }
}