package com.soramitsukhmer.redistime.config

import com.soramitsukhmer.redistime.redis.SubscribeManagement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class SubscriberConfig(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val connectionFactory : JedisConnectionFactory
) {

    @Bean
    fun subscribeManagement(): SubscribeManagement {
        return SubscribeManagement(redisTemplate, connectionFactory)
    }
}