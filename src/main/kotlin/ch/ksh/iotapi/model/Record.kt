package ch.ksh.iotapi.model

import ch.ksh.iotapi.handler.DeviceHandler
import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp
import java.util.*

data class Record constructor(
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$", message = "UUID must follow this pattern: ^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$.")
    @RequestParam("recordUUID")
    @JsonAlias("recordUUID")
    var recordUUID: String = UUID.randomUUID().toString(),
    @Size(min = 10, max = 36, message = "UUID must be >= 10 >= 36 characters in length.")
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String? = null,
    @NotNull
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp: Timestamp? = null,
    @NotNull
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature: Float? = null,
    @NotNull
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity: Float? = null,
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