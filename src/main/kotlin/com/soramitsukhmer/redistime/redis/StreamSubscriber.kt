package com.soramitsukhmer.redistime.redis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.stream.StreamListener
import java.util.function.Consumer


class StreamSubscriber<T>(
    private val clazzType: Class<T>,
    private val consumer: java.util.function.Consumer<T>
): StreamListener<String, ObjectRecord<String, T>> {
    private val mapper = jacksonObjectMapper()

    override fun onMessage(message: ObjectRecord<String, T>?) {
        val messageString = mapper.writeValueAsString(message?.value)
        wrapConsumer(consumer, clazzType).accept(messageString)
    }

    private fun <T> wrapConsumer(consumer: Consumer<T>, type: Class<T>): Consumer<String> {
        return Consumer { consumer.accept(mapper.readValue(it, type)) }
    }
}