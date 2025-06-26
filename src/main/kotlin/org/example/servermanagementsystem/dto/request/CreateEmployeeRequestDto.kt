package org.example.servermanagementsystem.dto.request

import java.util.*

data class CreateEmployeeRequestDto(
    val name: String,
    val email: String,
    val position: String,
    val departmentId: UUID
)
