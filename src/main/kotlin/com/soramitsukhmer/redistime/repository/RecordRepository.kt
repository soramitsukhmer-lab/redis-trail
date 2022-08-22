package com.soramitsukhmer.redistime.repository

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.repository.helper.RepositoryHelper
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.stereotype.Repository

@Repository
class RecordRepository(
    private val helper: RepositoryHelper<RecordEvent>
) {

    fun save(message: RecordEvent, metaStreamKey: String) : RecordEvent {
        message.createdAt  = System.currentTimeMillis()         // should removed to keep createdAt time from client and redis consistent
        return helper.generateAndSaveRecord(message.subject+"_"+message.subjectId.toString(), message.createdAt.toString(), message)
    }

    fun findAll(subject: String) : List<ObjectRecord<String, RecordEvent>>? {
        return helper.fetchRecords(subject, RecordEvent::class.java)
    }

    fun getAllBySubjectAndId(subject: String, id: Int) : List<ObjectRecord<String, RecordEvent>>? {
        val records = helper.fetchRecords(subject+"_"+id.toString(), RecordEvent::class.java)
//        val recordEvents = records?.map {
//            it.value
//        }
        return records
    }

    fun deleteMetaRecord(streamKey: String, time: String, event: RecordEvent) : Boolean {
        return helper.deleteEventFromMetaRecord(streamKey, time, event)
    }

}