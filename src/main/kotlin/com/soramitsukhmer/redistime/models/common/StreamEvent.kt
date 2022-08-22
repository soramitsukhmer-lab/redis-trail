package com.soramitsukhmer.redistime.models.common

import java.io.Serializable

interface StreamEvent: Serializable {
    fun streamKey(): String
    fun groupName(): String
}