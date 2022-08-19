package com.soramitsukhmer.redistime.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class StreamPublisherConfig {
    @Bean
    fun publisherTemplate(): ReactiveRedisTemplate<String, Any> {
        val config = RedisStandaloneConfiguration()
        config.hostName = "localhost"
        config.port = 6379
        val clientConfig = LettuceClientConfiguration.builder().build()
        val connectionFactory = LettuceConnectionFactory(config, clientConfig)
        connectionFactory.afterPropertiesSet()
        val keySerializer = StringRedisSerializer()
        val valueSerializer: Jackson2JsonRedisSerializer<Any> = Jackson2JsonRedisSerializer(Any::class.java)
        val builder: RedisSerializationContextBuilder<String, Any> = RedisSerializationContext.newSerializationContext(keySerializer)
        val context: RedisSerializationContext<String, Any> = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(connectionFactory, context)
    }

}