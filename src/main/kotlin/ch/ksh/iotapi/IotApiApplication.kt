package ch.ksh.iotapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = arrayOf(DataSourceAutoConfiguration::class))
class IotApiApplication

fun main(args: Array<String>) {
    runApplication<IotApiApplication>(*args)
}
