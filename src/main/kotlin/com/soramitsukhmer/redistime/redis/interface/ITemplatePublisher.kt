package com.soramitsukhmer.redistime.redis.`interface`

interface ITemplatePublisher {
    fun publish(message: Any)
    fun publishStream(streamKey: String, message: Any)
}