package com.soramitsukhmer.redistime.config

import com.soramitsukhmer.redistime.redis.SubscribeManagement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig(applicationProperties: ApplicationProperties){

    private val redisConfig = applicationProperties.redisConfig

    @Bean
    fun connectionFactory() : JedisConnectionFactory{
        val config = RedisStandaloneConfiguration()
        config.hostName = redisConfig.host
        config.port = redisConfig.port
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory())
        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = JdkSerializationRedisSerializer()
        template.hashValueSerializer = JdkSerializationRedisSerializer()
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun subscribeManagement(): SubscribeManagement {
        return SubscribeManagement(redisTemplate(), connectionFactory())
    }

}