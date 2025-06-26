package org.example.servermanagementsystem.dto.response

import java.util.*

data class ServerResponseDto(
    val id: UUID,
    val name: String,
    val manufacturer: String,
    val ip: String,
    val ramGb: Int,
    val diskGb: Int,
    val responsibleId: UUID
)