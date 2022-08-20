package com.soramitsukhmer.redistime.redis.`interface`

import com.soramitsukhmer.redistime.models.common.StreamEvent

interface IStreamPublisher {
    fun publish(message: StreamEvent)
}