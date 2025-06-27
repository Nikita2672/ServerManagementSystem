package org.example.servermanagementsystem.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.servermanagementsystem.dto.request.CreateEmployeeRequestDto
import org.example.servermanagementsystem.dto.request.UpdateEmployeeRequestDto
import org.example.servermanagementsystem.dto.response.EmployeeResponseDto
import org.example.servermanagementsystem.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "Employee Management", description = "API для управления сотрудниками")
@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @Operation(
        summary = "Создать сотрудника",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = CreateEmployeeRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Сотрудник успешно создан"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
        ]
    )
    @PostMapping
    fun createEmployee(
        @RequestBody @Valid createEmployeeRequestDto: CreateEmployeeRequestDto
    ): EmployeeResponseDto {
        return employeeService.createEmployee(createEmployeeRequestDto)
    }

    @Operation(
        summary = "Получить сотрудника по ID",
        parameters = [Parameter(name = "employeeId", description = "ID сотрудника", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Сотрудник найден"),
            ApiResponse(responseCode = "404", description = "Сотрудник не найден")
        ]
    )
    @GetMapping("/{employeeId}")
    fun getEmployeeById(
        @PathVariable employeeId: UUID
    ): EmployeeResponseDto {
        return employeeService.findEmployeeById(employeeId)
    }

    @Operation(
        summary = "Получить всех сотрудников по ID компании",
        parameters = [Parameter(name = "companyId", description = "ID компании", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Список сотрудников получен"),
            ApiResponse(responseCode = "404", description = "Компания не найдена")
        ]
    )
    @GetMapping("/by-company/{companyId}")
    fun getEmployeeByCompanyId(
        @PathVariable companyId: UUID
    ): List<EmployeeResponseDto> {
        return employeeService.findEmployeesByCompanyId(companyId)
    }

    @Operation(
        summary = "Обновить данные сотрудника",
        parameters = [Parameter(name = "employeeId", description = "ID сотрудника", required = true)],
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = UpdateEmployeeRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Сотрудник успешно обновлён"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            ApiResponse(responseCode = "404", description = "Сотрудник не найден")
        ]
    )
    @PutMapping("/{employeeId}")
    fun updateEmployee(
        @PathVariable employeeId: UUID,
        @RequestBody @Valid updateEmployeeRequestDto: UpdateEmployeeRequestDto
    ): EmployeeResponseDto {
        return employeeService.updateEmployee(employeeId, updateEmployeeRequestDto)
    }

    @Operation(
        summary = "Удалить сотрудника",
        parameters = [Parameter(name = "employeeId", description = "ID сотрудника", required = true)],
        responses = [
            ApiResponse(responseCode = "204", description = "Сотрудник успешно удалён"),
            ApiResponse(responseCode = "404", description = "Сотрудник не найден")
        ]
    )
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEmployee(
        @PathVariable employeeId: UUID
    ) {
        employeeService.deleteEmployee(employeeId)
    }
}
