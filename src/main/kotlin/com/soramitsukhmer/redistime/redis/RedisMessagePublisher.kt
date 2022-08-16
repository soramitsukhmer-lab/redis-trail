package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.redis.`interface`.MessagePublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(
    private val template: RedisTemplate<Any, Any>,
    private val chTopic: ChannelTopic
): MessagePublisher {

    /** NOTE:
     *  As the current application current scope,
     *  I think we don't need to publish anything.
     *  We only need just to subscribe from client
     */
    override fun publish(message: String) {
        template.convertAndSend(chTopic.topic, message)
    }
}