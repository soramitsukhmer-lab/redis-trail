package com.soramitsukhmer.redistime.models

import java.io.Serializable
import java.time.LocalDateTime

data class Record(
    val subject: String,
    val subjectId: Long,
    val data: Map<String, Any>,
    val action: String,
    val createdBy: Long,
    var createdAt: Long,
): Serializable
{
    init {
        require(subject.isNotBlank())
        require(subjectId > 0) {"subjectId is  required"}
        require(subject.length < 10){"subject maximum length is 10"}
        require(action.isNotBlank())
        require(data.keys.isNotEmpty()) {"data keys are required"}
    }
}