package com.soramitsukhmer.redistime.redis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.soramitsukhmer.redistime.models.Record
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class RedisMessageSubscriber: MessageListener {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val mapper = jacksonObjectMapper()

    /** NOTE:
     *  I think we need to save message to ProductRepository - (Please rename this repository to ObjectRepository)
     *  We don't need to save it into list.
     *  Before save, we need to apply the version to object
     */
    val messageList = mutableListOf<String>()
    override fun onMessage(message: Message, pattern: ByteArray?) {
        messageList.add(message.toString())
        val body = String(message.body, StandardCharsets.UTF_8)
        val record = mapper.readValue(body,Record::class.java)
        log.info("Message Received: $record")
    }

}