package com.soramitsukhmer.redistime.models.consumers

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.redis.SubscribeManagement
import com.soramitsukhmer.redistime.repository.RecordRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.stream.Subscription

@Configuration
class RecordEventConsumer(
    val subscribeManagement: SubscribeManagement,
    val recordRepository: RecordRepository
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun subscribeRecordEvent(): Subscription {
        // As Meta Stream of RECORD_EVENT
        return subscribeManagement.startSubscribe(
            RecordEvent.RECORD_EVENT.groupName,             // For further different group, different consumer
            RecordEvent.RECORD_EVENT,
            java.util.function.Consumer {
                /***
                 * Where we subscribe and handle message
                 * ***/
                logger.info("Message Received: $it")
                /***
                 * This will generate another stream based on product and id
                 */
                recordRepository.save(it, RecordEvent.RECORD_EVENT.streamKey)
                recordRepository.deleteMetaRecord(RecordEvent.RECORD_EVENT.streamKey, it.publishTimestamp, it)
            }
        )
    }
}