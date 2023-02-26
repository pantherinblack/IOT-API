package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import lombok.Getter
import lombok.Setter
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

data class Device(
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID : String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceName")
    @JsonAlias("deviceName")
    var deviceName : String = "unknown",
    @Getter
    @Setter
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    var latitude : Float,
    @Getter
    @Setter
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    var longitude : Float
) {
    //TODO
}