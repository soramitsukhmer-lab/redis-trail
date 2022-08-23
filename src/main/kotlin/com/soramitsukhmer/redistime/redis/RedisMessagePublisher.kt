package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.models.common.StreamEvent
import com.soramitsukhmer.redistime.redis.`interface`.ITemplatePublisher
import com.soramitsukhmer.redistime.repository.RecordRepository
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.RecordId
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(
    private val template: RedisTemplate<String, Any>,
    private val chTopic: ChannelTopic,
    private val subscribeManagement: SubscribeManagement,
    private val recordRepository: RecordRepository
): ITemplatePublisher {

    /** NOTE:
     *  As the current application current scope,
     *  I think we don't need to publish anything.
     *  We only need just to subscribe from client
     */
    override fun publish(message: Any) {
        template.convertAndSend(chTopic.topic, message)
    }

    override fun publishStream(message: StreamEvent) {
        subscribeIfNotExistConsumer(message.streamKey(), message.groupName())
        val record: ObjectRecord<String, StreamEvent> = StreamRecords.newRecord()
            .`in`(message.streamKey())
            .ofObject(message)
            .withId(RecordId.of(message.publishTimestamp+"-0"))
        template.opsForStream<String, Any>().add(record)
    }

    private fun subscribeIfNotExistConsumer(streamKey: String, groupName: String) {
        val emptyGroup = try {
            template.opsForStream<String, Any>().consumers(streamKey, groupName).isEmpty
        } catch (e: Exception) {
            true
        }
        if (emptyGroup) {
            subscribeManagement.startSubscribe(
                groupName,
                RecordEvent.RECORD_EVENT
            ) {
                recordRepository.saveAndDeleteMetaRecord(it, streamKey, it.publishTimestamp)
            }
        }
    }

}