package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import lombok.Getter
import lombok.Setter
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

data class Device constructor(
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID: String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceName")
    @JsonAlias("deviceName")
    var deviceName: String? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude: Float? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude: Float? = null
) {
    //TODO
}