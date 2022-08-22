package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.redis.RedisMessagePublisher
import com.soramitsukhmer.redistime.redis.RedisMessageSubscriber
import com.soramitsukhmer.redistime.repository.RecordRepository
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.web.bind.annotation.*

/** NOTE:
 *  This portal is only for testing purpose.
 *  We will remove it after testing pub/sub is done.
 */

@RestController
@RequestMapping("/api/v1/records")
class RecordPortal(
    private val productRepo: RecordRepository,
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
){
    @PostMapping
    fun save(@RequestBody record: RecordEvent) : RecordEvent {
//        println(record.data.isEmpty())
//        return record
        return productRepo.save(record)
    }

//    @GetMapping("/{subject}")
//    fun findAll(@PathVariable subject: String) : List<RecordEvent> {
//        return productRepo.findAll(subject)
//    }
//
    @GetMapping("/{subject}/{id}")
    fun findById(@PathVariable subject: String , @PathVariable id: Int) : List<ObjectRecord<String, RecordEvent>>? {
        return productRepo.getAllBySubjectAndId(subject,  id)
    }
//
//    @DeleteMapping("/{subject}/{id}")
//    fun deleteById(@PathVariable subject: String ,@PathVariable id: Int) : Boolean {
//        return productRepo.deleteById(subject , id)
//    }

    @GetMapping("/range/{subject}/{id}")
    fun findRangeRecord(
        @PathVariable subject: String ,
        @PathVariable id: Int ,
        @RequestParam("from") from: String?,
        @RequestParam("to") to: String?,
    ) : List<ObjectRecord<String, RecordEvent>>? {
        return productRepo.getRangeRecords(
            subject + "_" + id.toString(),
            Range.closed(from ?: "-", to ?: "+")
        )
    }
}