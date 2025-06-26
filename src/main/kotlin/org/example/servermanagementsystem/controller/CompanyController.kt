package org.example.servermanagementsystem.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.servermanagementsystem.dto.request.CreateCompanyRequestDto
import org.example.servermanagementsystem.dto.request.UpdateCompanyRequestDto
import org.example.servermanagementsystem.dto.response.CompanyResponseDto
import org.example.servermanagementsystem.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "Company Management", description = "API для управления компаниями")
@RestController
@RequestMapping("/api/v1/company")
class CompanyController(
    private val companyService: CompanyService
) {

    @Operation(
        summary = "Создать компанию",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = CreateCompanyRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Компания успешно создана"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
        ]
    )
    @PostMapping
    fun createCompany(
        @RequestBody createCompanyRequestDto: CreateCompanyRequestDto
    ): CompanyResponseDto {
        return companyService.createCompany(createCompanyRequestDto)
    }

    @Operation(
        summary = "Получить компанию по ID",
        parameters = [Parameter(name = "companyId", description = "ID компании", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Компания найдена"),
            ApiResponse(responseCode = "404", description = "Компания не найдена")
        ]
    )
    @GetMapping("/{companyId}")
    fun getCompany(
        @PathVariable companyId: UUID
    ): CompanyResponseDto {
        return companyService.getCompany(companyId)
    }

    @Operation(
        summary = "Обновить компанию",
        parameters = [Parameter(name = "companyId", description = "ID компании", required = true)],
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = UpdateCompanyRequestDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Компания успешно обновлена"),
            ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            ApiResponse(responseCode = "404", description = "Компания не найдена")
        ]
    )
    @PutMapping("/{companyId}")
    fun updateCompany(
        @PathVariable companyId: UUID,
        @RequestBody updateCompanyRequestDto: UpdateCompanyRequestDto
    ): CompanyResponseDto {
        return companyService.updateCompany(companyId, updateCompanyRequestDto)
    }

    @Operation(
        summary = "Удалить компанию",
        parameters = [Parameter(name = "companyId", description = "ID компании", required = true)],
        responses = [
            ApiResponse(responseCode = "204", description = "Компания успешно удалена"),
            ApiResponse(responseCode = "404", description = "Компания не найдена")
        ]
    )
    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCompany(
        @PathVariable companyId: UUID
    ) {
        companyService.deleteCompany(companyId)
    }
}
