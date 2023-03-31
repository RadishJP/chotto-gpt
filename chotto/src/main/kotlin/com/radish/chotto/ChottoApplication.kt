package com.radish.chotto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChottoApplication

fun main(args: Array<String>) {
    runApplication<ChottoApplication>(*args)
}
