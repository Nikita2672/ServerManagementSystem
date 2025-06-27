package org.example.servermanagementsystem.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCompanyRequestDto(
    @field:NotBlank(message = "Название компании не может быть пустым")
    @field:Size(min = 2, max = 100, message = "Название компании должно быть от 2 до 100 символов")
    val name: String,
    val departments: List<CreateDepartmentRequestDto>
)
