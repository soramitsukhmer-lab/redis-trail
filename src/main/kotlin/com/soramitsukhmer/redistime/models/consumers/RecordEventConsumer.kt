package com.soramitsukhmer.redistime.models.consumers

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.redis.SubscribeManagement
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.stream.Subscription

@Configuration
class RecordEventConsumer(
    val subscribeManagement: SubscribeManagement
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun subscribeRecordEvent(): Subscription {
        return subscribeManagement.startSubscribe(
            RecordEvent.RECORD_EVENT,
            java.util.function.Consumer {
                /***
                 * Where we subscribe and handle message
                 * ***/
                logger.info("Message Received: $it")
            }
        )
    }
}