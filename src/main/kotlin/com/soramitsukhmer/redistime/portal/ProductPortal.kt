package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.Product
import com.soramitsukhmer.redistime.redis.RedisMessagePublisher
import com.soramitsukhmer.redistime.redis.RedisMessageSubscriber
import com.soramitsukhmer.redistime.repository.ProductRepository
import org.slf4j.LoggerFactory
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
@RequestMapping("/api/v1/products")
class ProductPortal(
    private val productRepo: ProductRepository,
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
){
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun save(@RequestBody product: Product) : Product{
        return productRepo.save(product)
    }

    @GetMapping
    fun findAll() : List<Product> {
        redisMessagePublisher.publish("Received Request From Client: Endpoint(/api/v1/people, GET)")
        return productRepo.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : Product? {
        log.info("All Messages: ${redisMessageSubscriber.messageList}")
        return productRepo.findById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int) : Boolean {
        return productRepo.deleteById(id)
    }
}