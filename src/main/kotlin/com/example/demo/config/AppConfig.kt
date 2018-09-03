package com.example.demo.config

import com.example.demo.eventListener.BeforeSaveListener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
@Configuration
class AppConfig{
    @Bean fun beforeSaveListener() =  BeforeSaveListener()
}
