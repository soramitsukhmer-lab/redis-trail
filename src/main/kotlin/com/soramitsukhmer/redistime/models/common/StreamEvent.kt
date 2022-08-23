package com.soramitsukhmer.redistime.models.common

import java.io.Serializable

interface StreamEvent: Serializable {
    val publishTimestamp: String

    fun streamKey(): String
    fun groupName(): String
}