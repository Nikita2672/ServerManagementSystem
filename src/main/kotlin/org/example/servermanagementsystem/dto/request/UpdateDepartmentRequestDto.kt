package org.example.servermanagementsystem.dto.request

import java.util.*

data class UpdateDepartmentRequestDto (
    val name: String,
    val companyId: UUID
)
