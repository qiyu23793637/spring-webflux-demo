package com.example.demo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Person(
        @Id var id:Long = -1L,
        var name:String
)
