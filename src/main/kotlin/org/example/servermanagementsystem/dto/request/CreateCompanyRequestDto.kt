package org.example.servermanagementsystem.dto.request

data class CreateCompanyRequestDto(
    val name: String,
    val departments: List<CreateDepartmentRequestDto>
)
