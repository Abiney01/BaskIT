package com.app.baskit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BaskitApplication

fun main(args: Array<String>) {
	runApplication<BaskitApplication>(*args)
}
