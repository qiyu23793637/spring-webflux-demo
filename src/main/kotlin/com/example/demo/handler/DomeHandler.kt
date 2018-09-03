package com.example.demo.handler

import com.example.demo.document.Person
import com.example.demo.repository.DomeRepository
import com.example.demo.util.json
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.BodyExtractors
import reactor.core.publisher.toMono


@Service
class DomeHandler(val domeRepository: DomeRepository){

    fun all(req: ServerRequest) = ok().json().body(domeRepository.findAll(), Person::class.java)

    fun save(req: ServerRequest) = req.body(BodyExtractors.toMono(Person::class.java)).flatMap {
        domeRepository.insert(it).then(ok().body(it.toMono(),Person::class.java))
    }

    fun update(req: ServerRequest) = req.body(BodyExtractors.toMono(Person::class.java)).flatMap {
        domeRepository.save(it).then(ok().body(it.toMono(), Person::class.java))
    }

    fun delete(req: ServerRequest) = domeRepository.deleteById(req.pathVariable("id").toLong()).then(ok().json().body(true.toMono(),Boolean::class.java))
}