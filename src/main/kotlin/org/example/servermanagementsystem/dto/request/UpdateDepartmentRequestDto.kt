package org.example.servermanagementsystem.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class UpdateDepartmentRequestDto(
    @field:NotBlank(message = "Название отдела не может быть пустым")
    @field:Size(min = 2, max = 100, message = "Название отдела должно быть от 2 до 100 символов")
    val name: String,
    val companyId: UUID
)
