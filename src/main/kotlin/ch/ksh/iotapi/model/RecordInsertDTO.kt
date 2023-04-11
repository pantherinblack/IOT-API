package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp

data class RecordInsertDTO constructor(
    @Size(min = 10, max = 36, message = "UUID must be >= 10 >= 36 characters in length.")
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String,
    @NotNull
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp: Timestamp,
    @NotNull
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature: Float,
    @NotNull
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity: Float,
    @NotNull
    @RequestParam("batteryv")
    @JsonAlias("batteryv")
    var batteryv: Float,
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float,
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float,
    @NotNull
    @RequestParam("key")
    @JsonAlias("key")
    var key: String
)