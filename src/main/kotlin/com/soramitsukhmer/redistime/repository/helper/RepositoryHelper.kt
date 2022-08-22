package com.soramitsukhmer.redistime.repository.helper

import org.springframework.data.redis.connection.stream.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.awt.print.Book

import org.springframework.util.CollectionUtils


@Service
class RepositoryHelper<T>(
    private val template: RedisTemplate<String, Any>
){
    fun generateAndSaveRecord(streamKey: String, event: T): T {
        val record = StreamRecords.newRecord()
            .`in`(streamKey)
            .ofObject(event)
            .withId(RecordId.autoGenerate())
        template.opsForStream<String, Any>().add(record)
        return event
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
}