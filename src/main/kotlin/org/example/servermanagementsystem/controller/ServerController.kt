package org.example.servermanagementsystem.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.example.servermanagementsystem.dto.response.ServerResponseDto
import org.example.servermanagementsystem.service.ServerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "Server Management", description = "API для управления серверами")
@RestController
@RequestMapping("/api/v1/servers")
class ServerController(
    private val serverService: ServerService
) {

    @Operation(
        summary = "Создать сервер",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = CreateServerRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Сервер успешно создан"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
        ]
    )
    @PostMapping
    fun createServer(
        @RequestBody createServerRequestDto: CreateServerRequestDto
    ): ServerResponseDto {
        return serverService.createServer(createServerRequestDto)
    }

    @Operation(
        summary = "Получить список серверов по ID отдела",
        parameters = [Parameter(name = "departmentId", description = "ID отдела", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Список серверов получен"),
            ApiResponse(responseCode = "404", description = "Отдел не найден")
        ]
    )
    @GetMapping("/by-department/{departmentId}")
    fun getServersByDepartment(
        @PathVariable departmentId: UUID
    ): List<ServerResponseDto> {
        return serverService.findServersByDepartment(departmentId)
    }

    @Operation(
        summary = "Обновить сервер",
        parameters = [Parameter(name = "id", description = "ID сервера", required = true)],
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = CreateServerRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Сервер успешно обновлён"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            ApiResponse(responseCode = "404", description = "Сервер не найден")
        ]
    )
    @PutMapping("/{id}")
    fun updateServer(
        @PathVariable id: UUID,
        @RequestBody updateServerRequestDto: CreateServerRequestDto
    ): ServerResponseDto {
        return serverService.updateServer(id, updateServerRequestDto)
    }

    @Operation(
        summary = "Удалить сервер",
        parameters = [Parameter(name = "id", description = "ID сервера", required = true)],
        responses = [
            ApiResponse(responseCode = "204", description = "Сервер успешно удалён"),
            ApiResponse(responseCode = "404", description = "Сервер не найден")
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteServer(
        @PathVariable id: UUID
    ) {
        serverService.deleteServer(id)
    }
}
