package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.Record
import com.soramitsukhmer.redistime.redis.`interface`.IStreamPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/publish")
class PublisherPortal(
    private val streamPublisher: IStreamPublisher
) {

    @PostMapping("/stream")
    fun publishStream(@RequestBody record: Record) : Record {
        streamPublisher.publish(record)
        return record
    }
}