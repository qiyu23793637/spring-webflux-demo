package com.example.demo

import com.example.demo.document.Person
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.util.concurrent.TimeUnit
import org.junit.After
import org.slf4j.LoggerFactory
import reactor.core.Disposable
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    val log = LoggerFactory.getLogger(DemoApplicationTests::class.java)!!

    val client = WebClient.create("http://localhost:8080")

    /**
     * 异步需要等待否则会直接结束看不到结果
     */
    fun sleep() = TimeUnit.SECONDS.sleep(10)

    fun `save Test before`() = client.post().uri("/api/users").body(Person(name="test").toMono(),Person::class.java).retrieve()
            .bodyToMono(Person::class.java).log()
            .flatMap { Mono.just(it) }


    fun `all Test`() =
        client.get().uri("/api/users").retrieve()
                .bodyToFlux(Person::class.java).log()
                .flatMap {Mono.just(it) }.subscribe { log.info("all Test($it)") }

    fun `save Test`() =
        `save Test before`().subscribe{log.info("save Test($it)")}

    /**
     * 先保存了一个，然后修改 name。id为 @see com.example.demo.util.Sequence 在 @see com.example.demo.eventListener.BeforeSaveListener 中设置
     */
    fun `update Test`() =
        `save Test before`().subscribe {
            client.put().uri("/api/users").body(it.copy(name = "testUpdate").toMono(), Person::class.java).retrieve()
                    .bodyToMono(Person::class.java).log()
                    .flatMap { Mono.just(it) }.subscribe { log.info("update Test($it)") }
        }

    fun `delete Test`() =
        `save Test before`().subscribe {
            client.delete().uri("/api/users/${it.id}").retrieve()
                    .bodyToMono(Boolean::class.java).log()
                    .flatMap { Mono.just(it) }.subscribe { log.info("delete Test($it)") }
        }

    @Test
    fun `test`(){
        var allTest = `all Test`()
        var saveTest = `save Test`()
        //sleep()
        //var updateTest = `update Test`()
        //var deleteTest = `delete Test`()
        sleep()
        saveTest.dispose()
        //updateTest.dispose()
        //deleteTest.dispose()
        allTest.dispose()
    }
}


