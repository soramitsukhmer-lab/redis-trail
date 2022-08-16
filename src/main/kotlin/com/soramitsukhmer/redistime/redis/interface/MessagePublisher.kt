package com.soramitsukhmer.redistime.redis.`interface`

interface MessagePublisher {
    fun publish(message: String)
}