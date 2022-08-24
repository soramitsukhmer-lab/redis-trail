package com.soramitsukhmer.redistime.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
class ApplicationProperties(
    val redisConfig: RedisConfig = RedisConfig()
){
    data class RedisConfig(
        var host: String = "",
        var port: Int = 0,
    )
}