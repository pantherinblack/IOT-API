package ch.ksh.iotapi.model

import ch.ksh.iotapi.handler.DeviceHandler
import com.fasterxml.jackson.annotation.JsonAlias
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp
import java.util.*

data class Record constructor(
    @RequestParam("recordUUID")
    @JsonAlias("recordUUID")
    var recordUUID: String = UUID.randomUUID().toString(),
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String? = null,
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp: Timestamp? = null,
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature: Float? = null,
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity: Float? = null,
    @RequestParam("batteryv")
    @JsonAlias("batteryv")
    var batteryv: Float? = null
) {
    private var device: Device? = null

    fun getDevice(): Device? {
        device = DeviceHandler.getInstance().getDeviceByUUID(deviceUUID)
        return device
    }
}