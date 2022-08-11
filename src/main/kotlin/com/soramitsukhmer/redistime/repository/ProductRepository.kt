package com.soramitsukhmer.redistime.repository

import com.soramitsukhmer.redistime.models.Product
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    private val template: RedisTemplate<Any, Any>
){

    private val HASH_KEY = "Product"

    fun save(product: Product) : Product{
        template.opsForHash<Any, Any>().put(HASH_KEY, product.id, product)
        return product
    }

    fun findAll() : List<Product> {
        return template.opsForHash<Any, Product>().values(HASH_KEY)
    }

    fun findById(id: Int) : Product? {
        return template.opsForHash<Any, Product>().get(HASH_KEY, id)
    }

    fun deleteById(id: Int) : Boolean {
        return kotlin.runCatching { template.opsForHash<Any, Product>().delete(HASH_KEY, id) }.fold(
            onFailure = { false },
            onSuccess = { true }
        )
    }
}