package ch.ksh.iotapi.model

import ch.ksh.iotapi.handler.DeviceHandler
import com.fasterxml.jackson.annotation.JsonAlias
import lombok.Getter
import lombok.Setter
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp
import java.util.*

data class Record constructor(
    @Getter
    @Setter
    @NotNull
    @RequestParam("recordUUID")
    @JsonAlias("recordUUID")
    var recordUUID: String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp: Timestamp? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature: Float? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity: Float? = null,
    @Getter
    @Setter
    @NotNull
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