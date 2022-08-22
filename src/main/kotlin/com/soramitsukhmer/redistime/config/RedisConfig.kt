package com.soramitsukhmer.redistime.config

import com.soramitsukhmer.redistime.redis.RedisMessageSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig {

    @Bean
    fun connectionFactory() : JedisConnectionFactory{
        val config = RedisStandaloneConfiguration()
        config.hostName = "localhost"
        config.port = 6379
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory())
        template.keySerializer = StringRedisSerializer()
//        template.hashKeySerializer = StringRedisSerializer()
        template.hashKeySerializer = JdkSerializationRedisSerializer()
//        template.valueSerializer = JdkSerializationRedisSerializer()
        template.hashValueSerializer = JdkSerializationRedisSerializer()
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun topic() : ChannelTopic {
        return ChannelTopic("messageQueue")
    }

    @Bean
    fun messageListener() : MessageListenerAdapter {
        return MessageListenerAdapter(RedisMessageSubscriber())
    }

    @Bean
    fun redisContainer() : RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory())
        container.addMessageListener(messageListener(), topic())
        return container
    }

//    @Bean
//    fun redisPublisher() : ITemplatePublisher {
//        return RedisMessagePublisher(redisTemplate(), topic(), subscribeManagement)
//    }

}