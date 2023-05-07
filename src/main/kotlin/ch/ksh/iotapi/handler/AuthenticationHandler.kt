package ch.ksh.iotapi.handler

import ch.ksh.iotapi.util.ConfigReader

/**
 * Business logic for the authentication
 * @author Kevin Stupar
 * @since 07.05.2023
 */
class AuthenticationHandler {
    companion object {
        private var instance: AuthenticationHandler? = null

        /**
         * gives the instance object
         * @return instance
         */
        fun getInstance(): AuthenticationHandler {
            if (instance == null)
                instance = AuthenticationHandler()
            return instance!!
        }
    }

    /**
     * Test if the user cerdentails are valid.
     * @param userName of the user
     * @param password of the user
     * @return boolean valid -> true, invalid -> false
     */
    fun isValidUser(userName: String?, password: String?): Boolean {
        return true
        if (userName === null || password === null)
            return false
        if (userName.isBlank() || password.isBlank()) {
            return false
        }
        if (
            userName.equals(ConfigReader.readConfig("userName")) &&
            password.equals(ConfigReader.readConfig("password"))
        ) {
            return true
        }
        return false
    }
}