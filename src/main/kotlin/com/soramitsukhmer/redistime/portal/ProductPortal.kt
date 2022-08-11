package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.Product
import com.soramitsukhmer.redistime.repository.ProductRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductPortal(
    private val productRepo: ProductRepository
){
    @PostMapping
    fun save(@RequestBody product: Product) : Product{
        return productRepo.save(product)
    }

    @GetMapping
    fun findAll() : List<Product> {
        return productRepo.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : Product? {
        return productRepo.findById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int) : Boolean {
        return productRepo.deleteById(id)
    }
}