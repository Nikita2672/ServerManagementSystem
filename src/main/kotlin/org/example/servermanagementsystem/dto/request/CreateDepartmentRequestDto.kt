package org.example.servermanagementsystem.dto.request

import java.util.UUID

data class CreateDepartmentRequestDto(
    val name: String,
    val companyId: UUID
)
