package com.soramitsukhmer.redistime.repository.helper

import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.RedisZSetCommands.Limit
import org.springframework.data.redis.connection.stream.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class RepositoryHelper<T>(
    private val template: RedisTemplate<String, Any>
){
    fun generateAndSaveRecord(streamKey: String, time: String, event: T): T {
        val record = generateRecord(streamKey, time, event)
        template.opsForStream<String, Any>().add(record)
        return event
    }

    fun generateRecord(streamKey: String, time: String, event: T): ObjectRecord<String, T> {
        return StreamRecords.newRecord()
            .`in`(streamKey)
            .ofObject(event)
            .withId(RecordId.of("$time-0"))
    }

    fun deleteEventFromMetaRecord(streamKey: String, time: String, event: T): Boolean {
        val record = generateRecord(streamKey, time, event)
        return kotlin.runCatching {
            template.opsForStream<String, Any>().delete(record)
        }.fold(
            onFailure = { false },
            onSuccess = { true }
        )
    }

    fun fetchRecords(streamKey: String, clazzType: Class<T>): List<ObjectRecord<String, T>>? {
        val streamReadOptions =
            StreamReadOptions.empty() // If there is no data, the blocking 1s blocking time needs to be less than the time configured by 'spring.redis.timeout'
                .block(Duration.ofMillis(1000)) // Block until data is obtained, and a timeout exception may be reported
                // .block(Duration.ofMillis(0))
                // Get 10 data at a time
                .count(10)
        val readOffset = StringBuilder("0-0")
        return template.opsForStream<String, Any>()
            .read(
                clazzType,
                streamReadOptions,
                StreamOffset.create(streamKey, ReadOffset.from(readOffset.toString()))
            )
    }

    fun fetchRangeRecords(targetType: Class<T>, streamKey: String, range: Range<String>) : List<ObjectRecord<String, T>>? {
        return template.opsForStream<String, T>()
            .range(
                targetType,
                streamKey,
                range
            )
    }
}