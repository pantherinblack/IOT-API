package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

data class Device constructor(
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String = UUID.randomUUID().toString(),
    @RequestParam("deviceName")
    @JsonAlias("deviceName")
    var deviceName: String? = null,
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float? = null,
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float? = null
)