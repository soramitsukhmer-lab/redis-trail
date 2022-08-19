package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.redis.`interface`.ITemplatePublisher
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(
    private val template: RedisTemplate<Any, Any>,
    private val chTopic: ChannelTopic
): ITemplatePublisher {

    /** NOTE:
     *  As the current application current scope,
     *  I think we don't need to publish anything.
     *  We only need just to subscribe from client
     */
    override fun publish(message: Any) {
        template.convertAndSend(chTopic.topic, message)
    }

    override fun publishStream(streamKey: String, message: Any) {
        val record: ObjectRecord<Any, Any> = StreamRecords.newRecord()
            .ofObject(message)
            .withStreamKey(streamKey)
        template.opsForStream<Any, Any>().add(record)
    }

}