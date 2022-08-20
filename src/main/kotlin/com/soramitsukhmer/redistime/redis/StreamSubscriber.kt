package com.soramitsukhmer.redistime.redis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.soramitsukhmer.redistime.models.common.StreamEvent
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.stream.StreamListener
import java.util.function.Consumer


class StreamSubscriber<T>(
    private val clazzType: Class<T>,
    private val consumer: java.util.function.Consumer<T>
): StreamListener<String, ObjectRecord<String, StreamEvent>> {
    private val mapper = jacksonObjectMapper()
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onMessage(message: ObjectRecord<String, StreamEvent>?) {
        val messageString = mapper.writeValueAsString(message?.value)
        log.info("On Message: $messageString")
        wrapConsumer(consumer, clazzType).accept(messageString)
    }

    private fun <T> wrapConsumer(consumer: Consumer<T>, type: Class<T>): Consumer<String> {
        return Consumer { consumer.accept(mapper.readValue(it, type)) }
    }
}