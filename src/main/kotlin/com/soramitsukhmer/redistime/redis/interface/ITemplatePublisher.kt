package com.soramitsukhmer.redistime.redis.`interface`

import com.soramitsukhmer.redistime.models.common.StreamEvent

interface ITemplatePublisher {
    fun publishStream(message: StreamEvent)
}