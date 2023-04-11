package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

data class Device constructor(
    @Size(max = 36, message = "UUID must be >= 36 characters in length.")
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String = UUID.randomUUID().toString(),
    @NotNull
    @Size(min = 1, max = 255, message = "Name must be >= 1 >= 255 characters in length.")
    @RequestParam("deviceName")
    @JsonAlias("deviceName")
    var deviceName: String? = null,
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float? = null,
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float? = null
)