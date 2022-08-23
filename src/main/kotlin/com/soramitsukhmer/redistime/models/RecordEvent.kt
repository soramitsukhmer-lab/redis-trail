package com.soramitsukhmer.redistime.models

import com.soramitsukhmer.redistime.models.common.EventConfig
import com.soramitsukhmer.redistime.models.common.StreamEvent

data class RecordEvent(
    val subject: String,
    val subjectId: Long,
    val data: Map<String, Any>,
    val action: String,
    val createdBy: Long,
    var createdAt: Long,
    override val publishTimestamp: String = System.currentTimeMillis().toString()
): StreamEvent {

    init {
        require(subject.isNotBlank())
        require(subjectId > 0) {"subjectId is required"}
        require(subject.length < 10){"subject maximum length is 10"}
        require(action.isNotBlank())
        require(data.keys.isNotEmpty()) {"data keys are required"}
        require(createdAt > 0) {"createdAt timestamp is required"}
    }

    override fun streamKey(): String = "RECORD_EVENT"

    override fun groupName(): String = "RECORD_GROUP"

    companion object {
        val RECORD_EVENT = EventConfig("RECORD_EVENT", "RECORD_GROUP", RecordEvent::class.java)
    }
}
