package org.example.servermanagementsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.UUID

@SpringBootApplication
class ServerManagementSystemApplication

fun main(args: Array<String>) {
    runApplication<ServerManagementSystemApplication>(*args)
}
