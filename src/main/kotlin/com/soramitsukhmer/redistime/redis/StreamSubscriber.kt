package com.soramitsukhmer.redistime.redis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.soramitsukhmer.redistime.models.Record
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service

@Service
class StreamSubscriber: StreamListener<String, ObjectRecord<String, Record>> {
    private val mapper = jacksonObjectMapper()
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onMessage(message: ObjectRecord<String, Record>?) {
        val message = mapper.writeValueAsString(message?.value)
        val record = mapper.readValue(message, Record::class.java)
        log.info("Message Received: $record")
    }
}