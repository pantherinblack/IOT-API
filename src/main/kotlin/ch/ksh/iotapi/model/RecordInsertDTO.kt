package ch.ksh.iotapi.model

import java.sql.Timestamp

data class RecordInsertDTO constructor(
    var deviceUUID: String? = null,
    var timestamp: Timestamp? = null,
    var temperature: Float? = null,
    var humidity: Float? = null,
    var batteryv: Float? = null,
    var latitude: Float? = null,
    var longitude: Float? = null,
    var key: String? = null
) {
    fun valid(): String? {
        var message = ""

        if (deviceUUID == null)
            return "deviceUUID must not be null"
        if (timestamp == null)
            return "timestamp must not be null"
        if (temperature == null)
            return "temperature must not be null"
        if (humidity == null)
            return "humidity must not be null"
        if (batteryv == null)
            return "batteryv must not be null"
        if (latitude == null)
            return "latitude must not be null"
        if (longitude == null)
            return "longitude must not be null"
        if (key == null)
            return "key must not be null"

        if (deviceUUID!!.length < 5 || deviceUUID!!.length > 36)
            message += "deviceUUID must be between 5 and 36 in length"
        if (temperature!! < -1000 || temperature!! > 1000)
            message += "temperature must be between -1000 and 1000"
        if (humidity!! < -1000 || humidity!! > 1000)
            message += "humidity must be between -1000 and 1000"
        if (batteryv!! < -1000 || batteryv!! > 1000)
            message += "batteryv must be between -1000 and 1000"
        if (latitude!! < -1000 || latitude!! > 1000)
            message += "latitude must be between -1000 and 1000"
        if (longitude!! < -1000 || longitude!! > 1000)
            message += "longitude must be between -1000 and 1000"
        if (message == "")
            return null
        return message
    }
}