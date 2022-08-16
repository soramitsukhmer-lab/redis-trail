package com.soramitsukhmer.redistime.repository

import com.soramitsukhmer.redistime.models.Record
import com.soramitsukhmer.redistime.models.Product
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RecordRepository(
    private val template: RedisTemplate<Any, Any>
){
    fun save(record: Record) : Record {

        record.createdAt  = System.currentTimeMillis();
        template.opsForHash<Any, Any>().put(record.subject, record.subjectId, record)
        return record
    }

    fun findAll(subject: String) : List<Record> {
        return template.opsForHash<String, Record>().values(subject)
    }

    fun findById(subject: String , id: Int) : Record? {
        return template.opsForHash<String, Record>().get(subject, id)
    }

    fun deleteById(subject: String, id: Int) : Boolean {
        return kotlin.runCatching { template.opsForHash<Any, Product>().delete(subject, id) }.fold(
            onFailure = { false },
            onSuccess = { true }
        )
    }

}