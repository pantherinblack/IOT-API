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
    private var recordUUID:String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("deviceUUID")
    @JsonAlias("deviceUUID")
    private var deviceUUID:String = UUID.randomUUID().toString(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("timestamp")
    @JsonAlias("timestamp")
    private var timestamp:LocalDateTime? = LocalDateTime.now(),
    @Getter
    @Setter
    @NotNull
    @RequestParam("temperature")
    @JsonAlias("temperature")
    private var temperature:Float? = null,
    @Getter
    @Setter
    @NotNull
    @RequestParam("humidity")
    @JsonAlias("humidity")
    private var humidity:Float? = null
) {
    //TODO
}