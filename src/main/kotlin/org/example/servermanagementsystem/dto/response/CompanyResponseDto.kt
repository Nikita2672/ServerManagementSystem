package org.example.servermanagementsystem.dto.response

import java.time.Instant
import java.util.*

data class CompanyResponseDto(
    val id: UUID,
    val name: String,
    val createdAt: Instant,
    val departments: List<DepartmentResponseDto>
)