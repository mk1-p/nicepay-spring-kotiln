package io.mk1.nicepay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NicepayApplication

fun main(args: Array<String>) {
    runApplication<NicepayApplication>(*args)
}
