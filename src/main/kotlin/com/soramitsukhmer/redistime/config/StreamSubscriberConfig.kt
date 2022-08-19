package com.soramitsukhmer.redistime.config

import com.soramitsukhmer.redistime.models.Record
import com.soramitsukhmer.redistime.redis.StreamSubscriber
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

@Configuration
class StreamSubscriberConfig(
    private val applicationProperties: ApplicationProperties,
    private val redisTemplate: RedisTemplate<Any, Any>,
    private val connectionFactory : JedisConnectionFactory
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val streamConfig = applicationProperties.streamConfig

    @Bean
    fun streamListenerContainer(): Subscription {
        try {
            redisTemplate.opsForStream<Any, Any>().createGroup(streamConfig.streamKey, streamConfig.group)
        } catch (e: Exception) {
            logger.warn("Group already exists ${streamConfig.group}")
        }
        val streamListener = StreamSubscriber()
        val options: StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Record>> =
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(Record::class.java)
                .build()
        val listenerContainer = StreamMessageListenerContainer
            .create(connectionFactory, options)
        val subscription: Subscription = listenerContainer.receive(
            Consumer.from(
                streamConfig.group, InetAddress.getLocalHost().hostName
            ),
            StreamOffset.create(streamConfig.streamKey, ReadOffset.lastConsumed()),
            streamListener
        )
        listenerContainer.start()
        return subscription
    }
}