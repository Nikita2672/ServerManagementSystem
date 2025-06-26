package org.example.servermanagementsystem.dto.response

import java.util.*

data class EmployeeResponseDto(
    val id: UUID,
    val name: String,
    val email: String,
    val position: String
)