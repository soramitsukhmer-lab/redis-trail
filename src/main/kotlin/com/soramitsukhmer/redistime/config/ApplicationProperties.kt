package com.soramitsukhmer.redistime.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
data class ApplicationProperties(
    val streamConfig: StreamConfig = StreamConfig()
) {
    data class StreamConfig(
        var streamKey: String = "",
        var group: String = ""
    )
}