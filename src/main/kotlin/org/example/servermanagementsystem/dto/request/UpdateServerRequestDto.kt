package org.example.servermanagementsystem.dto.request

import java.util.*

data class UpdateServerRequestDto (
    val id: UUID,
    val name: String,
    val manufacturer: String,
    val ip: String,
    val ramGb: Int,
    val diskGb: Int,
    val responsibleId: UUID
)