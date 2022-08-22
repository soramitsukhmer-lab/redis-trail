package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.models.common.EventConfig
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.hash.ObjectHashMapper
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import java.net.InetAddress
import java.time.Duration

class SubscribeManagement(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val connectionFactory : JedisConnectionFactory
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun <T> startSubscribe(
        groupName: String,
        event: EventConfig<T>,
        consumer: java.util.function.Consumer<T>
    ): Subscription {
        createGroup(event.streamKey, groupName)
        val streamListener = StreamSubscriber(event.classType, consumer)
        val options: StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, T>> =
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .keySerializer<String, ObjectRecord<String, T>>(StringRedisSerializer())
//                .hashKeySerializer<String, T>(StringRedisSerializer())
//                .hashValueSerializer<String, String>(StringRedisSerializer())
                .hashKeySerializer<Any, T>(JdkSerializationRedisSerializer())
                .hashValueSerializer<String, Any>(JdkSerializationRedisSerializer())
                .objectMapper(ObjectHashMapper())
                .targetType(event.classType)
                .build()
        val listenerContainer = StreamMessageListenerContainer
            .create(connectionFactory, options)
        val subscription: Subscription = listenerContainer.receive(
            Consumer.from(
                groupName, InetAddress.getLocalHost().hostName
            ),
            StreamOffset.create(event.streamKey, ReadOffset.lastConsumed()),
            streamListener
        )
        listenerContainer.start()
        logger.info("Initialized subscription of StreamKey[${event.streamKey}] and Group[$groupName]")
        return subscription
    }

    fun createGroup(streamKey: String, groupName: String) {
        try {
            redisTemplate.opsForStream<Any, Any>().createGroup(streamKey, groupName)
        } catch (e: Exception) {
            logger.warn("Group already exists $groupName")
        }
    }

    fun existOrCreateGroup(streamKey: String, groupName: String) {
        redisTemplate.opsForStream<Any, Any>().groups(streamKey).forEach { it.groupName() }
    }
}