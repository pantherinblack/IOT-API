package ch.ksh.iotapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

/**
 * Application runner
 * @author Kevin Stupar
 * @since 07.05.2023
 */
@SpringBootApplication(exclude = arrayOf(DataSourceAutoConfiguration::class))
class IotApiApplication

/**
 * runs the application
 *
 * @param args running arguments
 */
fun main(args: Array<String>) {
    runApplication<IotApiApplication>(*args)
}
