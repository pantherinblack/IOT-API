package ch.ksh.iotapi.model

import java.util.*

data class Device constructor(
    var deviceUUID: String = UUID.randomUUID().toString(),
    var deviceName: String? = null,
    var latitude: Float? = null,
    var longitude: Float? = null
) {
    fun valid(): String? {
        var message = ""
        if (deviceName == null)
            return "deviceName must not be null"
        if (latitude == null)
            return "latitude must not be null"
        if (longitude == null)
            return "longitude must not be null"

        if (deviceUUID.length < 5 || deviceUUID.length > 36)
            message += "deviceUUID must be between 5 and 36 in length"
        if (deviceName!!.length < 1 || deviceName!!.length > 255)
            message += "deviceName must be between 1 and 36 in length"
        if (latitude!! < -1000 || latitude!! > 1000)
            message += "latitude must be between -1000 and 1000"
        if (longitude!! < -1000 || longitude!! > 1000)
            message += "longitude must be between -1000 and 1000"
        if (message == "")
            return null
        return message
    }
}