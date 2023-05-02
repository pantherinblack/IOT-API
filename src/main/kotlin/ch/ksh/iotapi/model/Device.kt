package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
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
    @DecimalMin(value = "-1000.00", message = "Value must be >= -100")
    @DecimalMax(value = "1000.00", message = "Value must be >= 1000")
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float? = null,
    @DecimalMin(value = "-1000.00", message = "Value must be >= -100")
    @DecimalMax(value = "1000.00", message = "Value must be >= 1000")
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float? = null
) {
    fun valid(): String? {
        var message = ""
        if (deviceName==null)
            return "deviceName must not be null"
        if (latitude==null)
            return "latitude must not be null"
        if (longitude==null)
            return "longitude must not be null"

        if (deviceUUID.length<5 || deviceUUID.length>36)
            message+="deviceUUID must be between 5 and 36 in length"
        if (deviceName!!.length<1 || deviceName!!.length>255)
            message+="deviceName must be between 1 and 36 in length"
        if (latitude!! <-1000 || latitude!! >1000)
            message+="latitude must be between -1000 and 1000"
        if (longitude!! <-1000 || longitude!! >1000)
            message+="longitude must be between -1000 and 1000"
        if (message=="")
            return null
        return message
    }
}