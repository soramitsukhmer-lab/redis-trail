package com.soramitsukhmer.redistime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisTimeApplication

fun main(args: Array<String>) {
	runApplication<RedisTimeApplication>(*args)
}
