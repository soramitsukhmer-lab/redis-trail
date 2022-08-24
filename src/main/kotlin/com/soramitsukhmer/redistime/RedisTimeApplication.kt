package com.soramitsukhmer.redistime

import com.soramitsukhmer.redistime.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class RedisTimeApplication

fun main(args: Array<String>) {
	runApplication<RedisTimeApplication>(*args)
}
