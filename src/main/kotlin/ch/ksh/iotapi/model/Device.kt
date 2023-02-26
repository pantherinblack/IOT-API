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
    private var deviceUUID : String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceName")
    @JsonAlias("deviceName")
    private var deviceName : String = "Unknown",
    @Getter
    @Setter
    @NotNull
    @RequestParam("latitude")
    @JsonAlias("latitude")
    private var latitude : Float = 0f,
    @Getter
    @Setter
    @NotNull
    @RequestParam("longitude")
    @JsonAlias("longitude")
    private var longitude : Float = 0f
) {
    //TODO
}