package com.soramitsukhmer.redistime.models

data class Record(
    val subject: String,
    val subjectId: Long,
    val data: Map<String, Any>,
    val action: String,
    val createdBy: Long
){
    init {
        require(subject.isNotBlank())
        require(subject.length < 10){"subject maximum length is 10"}
        require(action.isNotBlank())
        require(data.keys.isNullOrEmpty())
    }
}