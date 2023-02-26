package ch.ksh.iotapi.model

import com.fasterxml.jackson.annotation.JsonAlias
import lombok.Getter
import lombok.Setter
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.util.UUID

data class Record(
    @Getter
    @Setter
    @NotNull
    @RequestParam("recordUUID")
    @JsonAlias("recordUUID")
    var recordUUID : String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    var deviceUUID : String,
    @Getter
    @Setter
    @NotNull
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    var timestamp : LocalDateTime = LocalDateTime.now(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("temperature")
    @JsonAlias("temperature")
    var temperature : Float,
    @Getter
    @Setter
    @NotNull
    @RequestParam("humidity")
    @JsonAlias("humidity")
    var humidity : Float,
    @Getter
    @Setter
    @NotNull
    @RequestParam("batteryv")
    @JsonAlias("batteryv")
    var batteryv : Float
) {
    //TODO
}