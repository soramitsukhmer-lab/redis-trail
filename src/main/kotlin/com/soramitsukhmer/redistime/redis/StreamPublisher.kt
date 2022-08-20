package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.models.common.StreamEvent
import com.soramitsukhmer.redistime.redis.`interface`.IStreamPublisher
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service


@Service
class StreamPublisher(
    private val template: ReactiveRedisTemplate<String, Any>
): IStreamPublisher {

    override fun publish(message: StreamEvent) {
        val record: ObjectRecord<String, StreamEvent> = StreamRecords.newRecord()
            .ofObject(message)
            .withStreamKey(message.streamKey())

        template.opsForStream<String, Any>()
            .add(record)
            .subscribe()
    }
}