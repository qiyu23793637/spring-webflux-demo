package com.example.demo.eventListener

import com.example.demo.util.Sequence
import org.slf4j.LoggerFactory
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent

class BeforeSaveListener : AbstractMongoEventListener<Any>() {

    private val log = LoggerFactory.getLogger(BeforeSaveListener::class.java)

    override fun onBeforeConvert(event: BeforeConvertEvent<Any>) {
        val source = event.source
        val declaredFields = source.javaClass.declaredFields
           for (f in declaredFields) {
            val annotation = f.getAnnotation(Id::class.java)
            if (annotation != null) {
                try {
                    f.isAccessible = true
                    val o = f.get(source) as? Long ?: break
                    //如果有id 就不要set 是更新
                    if (o != -1L) break
                    f.set(source, Sequence.getId())
                } catch (e: IllegalAccessException) {
                    log.error("ID赋值出错", e)
                }
            }
        }
        super.onBeforeConvert(event)
    }
}
