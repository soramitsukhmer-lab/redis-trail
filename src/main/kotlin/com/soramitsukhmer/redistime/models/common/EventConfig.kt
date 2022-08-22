package com.soramitsukhmer.redistime.models.common

class EventConfig<T>(
    val streamKey: String,
    val groupName: String,
    val classType: Class<T>
)