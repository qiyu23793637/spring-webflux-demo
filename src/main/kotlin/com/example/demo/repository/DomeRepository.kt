package com.example.demo.repository

import com.example.demo.document.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DomeRepository : ReactiveMongoRepository<Person,Long>