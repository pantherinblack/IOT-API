package ch.ksh.iotapi.handler

import ch.ksh.iotapi.util.ConfigReader

class AuthenticationHandler {
    companion object {
        private var instance: AuthenticationHandler? = null

        fun getInstance(): AuthenticationHandler {
            if (instance == null)
                instance = AuthenticationHandler()
            return instance!!
        }
    }

    fun isValidUser(userName: String, password: String): Boolean {
        if (userName.isBlank() || password.isBlank()) {
            return false
        }
        if (
            userName.equals(ConfigReader.readConfig("userName")) &&
                password.equals(ConfigReader.readConfig("password"))) {
            return true
        }
        return false
    }
}