package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.redis.`interface`.ITemplatePublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// NOTE: This portal is only for testing and demo purpose.
@RestController
@RequestMapping("/api/v1/publish")
class PublisherPortal(
    private val templatePublisher: ITemplatePublisher
) {

    @PostMapping("/stream")
    fun publishStream(@RequestBody record: RecordEvent) : RecordEvent {
        templatePublisher.publishStream(record)
        return record
    }
}