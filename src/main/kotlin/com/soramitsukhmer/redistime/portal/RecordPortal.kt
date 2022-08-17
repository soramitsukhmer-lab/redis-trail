package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.Record

import com.soramitsukhmer.redistime.redis.RedisMessagePublisher
import com.soramitsukhmer.redistime.redis.RedisMessageSubscriber
import com.soramitsukhmer.redistime.repository.RecordRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun save(@RequestBody record: Record) : Record {
//        println(record.data.isEmpty())
//        return record
        return productRepo.save(record)
    }

    @GetMapping("/{subject}")
    fun findAll(@PathVariable subject: String) : List<Record> {
        return productRepo.findAll(subject)
    }

    @GetMapping("/{subject}/{id}")
    fun findById(@PathVariable subject: String , @PathVariable id: Int) : Record? {
        return productRepo.findById(subject,  id)
    }

    @DeleteMapping("/{subject}/{id}")
    fun deleteById(@PathVariable subject: String ,@PathVariable id: Int) : Boolean {
        return productRepo.deleteById(subject , id)
    }
}