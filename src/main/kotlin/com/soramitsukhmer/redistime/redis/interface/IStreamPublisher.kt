package com.soramitsukhmer.redistime.redis.`interface`

interface IStreamPublisher {
    fun publish(message: Any)
}