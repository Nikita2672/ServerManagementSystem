package org.example.servermanagementsystem.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class CreateEmployeeRequestDto(
    @field:NotBlank(message = "Имя не может быть пустым")
    @field:Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    val name: String,

    @field:NotBlank(message = "Email не может быть пустым")
    @field:Email(message = "Email должен быть валидным адресом электронной почты")
    val email: String,

    @field:NotBlank(message = "Должность не может быть пустой")
    @field:Size(min = 2, max = 100, message = "Должность должна быть от 2 до 100 символов")
    val position: String,

    val departmentId: UUID
)
