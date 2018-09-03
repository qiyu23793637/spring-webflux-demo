package com.example.demo.route

import com.example.demo.handler.DomeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*


@Configuration
class DomeRoute(private val domeHandler: DomeHandler){
    @Bean
    fun route() = router {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                GET("/users", domeHandler::all)
                POST("/users",domeHandler::save)
                PUT("/users",domeHandler::update)
                DELETE("/users/{id}",domeHandler::delete)
            }
        }
    }
}