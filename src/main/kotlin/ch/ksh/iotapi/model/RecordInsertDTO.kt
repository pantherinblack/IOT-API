package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp

data class RecordInsertDTO constructor(
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String,
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp: Timestamp,
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature: Float,
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity: Float,
    @RequestParam("batteryv")
    @JsonAlias("batteryv")
    var batteryv: Float,
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float,
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float,
    @RequestParam("key")
    @JsonAlias("key")
    var key: String
)