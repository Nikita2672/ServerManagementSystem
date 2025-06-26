package org.example.servermanagementsystem.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.servermanagementsystem.dto.request.CreateDepartmentRequestDto
import org.example.servermanagementsystem.dto.request.UpdateDepartmentRequestDto
import org.example.servermanagementsystem.dto.response.DepartmentResponseDto
import org.example.servermanagementsystem.service.DepartmentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "Department Management", description = "API для управления отделами")
@RestController
@RequestMapping("api/v1/departments")
class DepartmentController(
    private val departmentService: DepartmentService
) {

    @Operation(
        summary = "Создать отдел",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = CreateDepartmentRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Отдел успешно создан"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
        ]
    )
    @PostMapping
    fun createDepartment(
        @RequestBody createDepartmentRequestDto: CreateDepartmentRequestDto
    ): DepartmentResponseDto {
        return departmentService.createDepartment(createDepartmentRequestDto)
    }

    @Operation(
        summary = "Получить отдел по ID",
        parameters = [Parameter(name = "departmentId", description = "ID отдела", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Отдел найден"),
            ApiResponse(responseCode = "404", description = "Отдел не найден")
        ]
    )
    @GetMapping("/{departmentId}")
    fun getDepartment(
        @PathVariable departmentId: UUID
    ): DepartmentResponseDto {
        return departmentService.getDepartmentById(departmentId)
    }

    @Operation(
        summary = "Обновить отдел",
        parameters = [Parameter(name = "departmentId", description = "ID отдела", required = true)],
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = UpdateDepartmentRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Отдел успешно обновлён"),
            ApiResponse(responseCode = "404", description = "Отдел не найден"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
        ]
    )
    @PutMapping("/{departmentId}")
    fun updateDepartment(
        @PathVariable departmentId: UUID,
        @RequestBody updateDepartmentRequestDto: UpdateDepartmentRequestDto
    ): DepartmentResponseDto {
        return departmentService.updateDepartment(departmentId, updateDepartmentRequestDto)
    }

    @Operation(
        summary = "Удалить отдел",
        parameters = [Parameter(name = "departmentId", description = "ID отдела", required = true)],
        responses = [
            ApiResponse(responseCode = "204", description = "Отдел удалён"),
            ApiResponse(responseCode = "404", description = "Отдел не найден")
        ]
    )
    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDepartment(
        @PathVariable departmentId: UUID
    ) {
        departmentService.deleteDepartment(departmentId)
    }
}
