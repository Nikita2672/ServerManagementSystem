package org.example.servermanagementsystem.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.util.*

data class UpdateServerRequestDto(
    @field:NotBlank(message = "Сервер не может быть пустым")
    @field:Size(min = 2, max = 100, message = "Сервер должно быть от 2 до 100 символов")
    val name: String,
    @field:NotBlank(message = "Производитель не может быть пустым")
    @field:Size(min = 2, max = 100, message = "Производитель должно быть от 2 до 100 символов")
    val manufacturer: String,
    @field:NotBlank(message = "IP-адрес не может быть пустым")
    @field:Pattern(
        regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\$|^((?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4}"
                + "|(?:[0-9A-Fa-f]{1,4}:){1,7}:|:(?::[0-9A-Fa-f]{1,4}){1,7}|(?:[0-9A-Fa-f]{1,4}:){1,6}:[0-9A-Fa-f]{1,4}|(?:[0-9A-Fa-f]{1,4}:){1,5}(?::[0-9A-Fa-f]{1,4}){1,2}|(?:[0-9A-Fa-f]{1,4}:){1,4}(?::[0-9A-Fa-f]{1,4}){1,3}|(?:[0-9A-Fa-f]{1,4}:){1,3}(?::[0-9A-Fa-f]{1,4}){1,4}|(?:[0-9A-Fa-f]{1,4}:){1,2}(?::[0-9A-Fa-f]{1,4}){1,5}|[0-9A-Fa-f]{1,4}:(?:(?::[0-9A-Fa-f]{1,4}){1,6})|:(?:(?::[0-9A-Fa-f]{1,4}){1,6}))\$",
        message = "IP-адрес должен быть в формате IPv4 или IPv6"
    )
    val ip: String,
    @field:Positive
    val ramGb: Int,
    @field:Positive
    val diskGb: Int,
    val responsibleId: UUID
)