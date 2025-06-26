package org.example.servermanagementsystem.dto.request

import java.util.UUID

data class UpdateEmployeeRequestDto (
    val name: String,
    val email: String,
    val position: String,
    val departmentId: UUID
)