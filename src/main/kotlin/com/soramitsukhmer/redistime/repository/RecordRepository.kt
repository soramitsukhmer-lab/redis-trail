package com.soramitsukhmer.redistime.repository

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.repository.helper.RepositoryHelper
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.stereotype.Repository

@Repository
class RecordRepository(
    private val helper: RepositoryHelper<RecordEvent>
) {

    fun saveAndDeleteMetaRecord(message: RecordEvent, metaStreamKey: String, time: String) : RecordEvent {
        message.createdAt  = System.currentTimeMillis() // TODO() should removed to keep createdAt time from client and redis consistent
        val streamKey = message.subject.uppercase() + "_" + message.subjectId.toString()
        helper.generateAndSaveRecord(streamKey, message.createdAt.toString(), message)
        helper.deleteEventFromMetaRecord(metaStreamKey, time, message)
        return message
    }

    fun getMetaRecords(metaStreamKey: String) : List<ObjectRecord<String, RecordEvent>>? {
        return helper.fetchRecords(metaStreamKey.uppercase(), RecordEvent::class.java)
    }

    fun getAllBySubjectAndId(subject: String, id: Int) : List<ObjectRecord<String, RecordEvent>>? {
        val streamKey = subject.uppercase() + "_" + id.toString()
        return helper.fetchRecords(streamKey, RecordEvent::class.java)
    }

    fun getRangeRecords(subject: String, id: Int, from: String?, to: String?) : List<ObjectRecord<String, RecordEvent>>? {
        val streamKey = subject.uppercase() + "_" + id.toString()
        val range = Range.closed(from ?: "-", to ?: "+")
        return helper.fetchRangeRecords(
            RecordEvent::class.java,
            streamKey,
            range
        )
    }
}