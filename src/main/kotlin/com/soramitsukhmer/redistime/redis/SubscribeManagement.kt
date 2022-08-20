package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.models.common.EventConfig
import com.soramitsukhmer.redistime.models.common.StreamEvent
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import java.net.InetAddress
import java.time.Duration

class SubscribeManagement(
    private val redisTemplate: RedisTemplate<Any, Any>,
    private val connectionFactory : JedisConnectionFactory
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun <T> startSubscribe(
        event: EventConfig<T>,
        consumer: java.util.function.Consumer<T>
    ): Subscription {
        try {
            redisTemplate.opsForStream<Any, Any>().createGroup(event.streamKey, event.groupName)
        } catch (e: Exception) {
            logger.warn("Group already exists ${event.groupName}")
        }
        val streamListener = StreamSubscriber(event.classType, consumer)
        val options: StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, StreamEvent>> =
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(StreamEvent::class.java)
                .build()
        val listenerContainer = StreamMessageListenerContainer
            .create(connectionFactory, options)
        val subscription: Subscription = listenerContainer.receive(
            Consumer.from(
                event.groupName, InetAddress.getLocalHost().hostName
            ),
            StreamOffset.create(event.streamKey, ReadOffset.lastConsumed()),
            streamListener
        )
        listenerContainer.start()
        return subscription
    }
}