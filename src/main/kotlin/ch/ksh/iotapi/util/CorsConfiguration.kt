package ch.ksh.iotapi.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Cors COnfiguration
 * @author Kevin Stupar
 * @since 07.05.2023
 */
@Configuration
@EnableWebFlux
class CorsConfiguration {
    /**
     * Configures the settings for CORS
     * @return configuration
     */
    @Bean
    fun corsFilter(): CorsWebFilter {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            maxAge = 3600L
        }
        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
        return CorsWebFilter(source)
    }
}