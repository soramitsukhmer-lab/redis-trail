package com.soramitsukhmer.redistime.repository

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.repository.helper.RepositoryHelper
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.stereotype.Repository

@Repository
class RecordRepository(
    private val helper: RepositoryHelper<RecordEvent>
) {

    fun save(message: RecordEvent) : RecordEvent {
        message.createdAt  = System.currentTimeMillis()
        return helper.generateAndSaveRecord(message.subject+"_"+message.subjectId.toString(), message)
    }

//    fun findAll(subject: String) : List<RecordEvent> {
//        return template.opsForHash<String, RecordEvent>().values(subject)
//    }
//
    fun getAllBySubjectAndId(subject: String, id: Int) : List<ObjectRecord<String, RecordEvent>>? {
        val records = helper.fetchRecords(subject+"_"+id.toString(), RecordEvent::class.java)
//        val recordEvents = records?.map {
//            it.value
//        }
        return records
    }
//
//    fun deleteById(subject: String, id: Int) : Boolean {
//        return kotlin.runCatching { template.opsForHash<Any, Product>().delete(subject, id.toString()) }.fold(
//            onFailure = { false },
//            onSuccess = { true }
//        )
//    }

}