package com.soramitsukhmer.redistime.redis.`interface`

import com.soramitsukhmer.redistime.models.common.StreamEvent

interface ITemplatePublisher {
    fun publish(message: Any)
    fun publishStream(message: StreamEvent)
}