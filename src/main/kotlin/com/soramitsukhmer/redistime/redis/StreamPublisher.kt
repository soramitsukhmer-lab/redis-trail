package com.soramitsukhmer.redistime.redis

import com.soramitsukhmer.redistime.config.ApplicationProperties
import com.soramitsukhmer.redistime.redis.`interface`.IStreamPublisher
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service


@Service
class StreamPublisher(
    private val template: ReactiveRedisTemplate<String, Any>,
    private val applicationProperties: ApplicationProperties
): IStreamPublisher {

    override fun publish(message: Any) {
        val record: ObjectRecord<String, Any> = StreamRecords.newRecord()
            .ofObject(message)
            .withStreamKey(applicationProperties.streamConfig.streamKey)
        template.opsForStream<String, Any>()
            .add(record)
            .subscribe()
    }
}