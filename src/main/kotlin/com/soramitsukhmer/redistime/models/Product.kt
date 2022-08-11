package com.soramitsukhmer.redistime.models

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.math.BigDecimal
import java.io.Serializable

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Product")
data class Product(
    @Id
    var id: Int,
    var name: String,
    var qty: Int,
    var price: BigDecimal
) : Serializable