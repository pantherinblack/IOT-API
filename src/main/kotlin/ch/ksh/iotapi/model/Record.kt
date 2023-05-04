package ch.ksh.iotapi.model

import ch.ksh.iotapi.handler.DeviceHandler
import java.sql.Timestamp
import java.util.*

data class Record constructor(
    var recordUUID: String = UUID.randomUUID().toString(),
    var deviceUUID: String? = null,
    var timestamp: Timestamp? = null,
    var temperature: Float? = null,
    var humidity: Float? = null,
    var batteryv: Float? = null
) {
    private var device: Device? = null

    fun getDevice(): Device? {
        device = DeviceHandler.getInstance().getDeviceByUUID(deviceUUID)
        return device
    }

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

        if (!recordUUID.matches(Regex("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$")))
            message += "recordUUID must match UUID syntax"
        if (deviceUUID!!.length < 5 || deviceUUID!!.length > 36)
            message += "deviceUUID must be between 5 and 36 in length"
        if (temperature!! < -1000 || temperature!! > 1000)
            message += "temperature must be between -1000 and 1000"
        if (humidity!! < -1000 || humidity!! > 1000)
            message += "humidity must be between -1000 and 1000"
        if (batteryv!! < -1000 || batteryv!! > 1000)
            message += "batteryv must be between -1000 and 1000"
        if (message == "")
            return null
        return message
    }
}