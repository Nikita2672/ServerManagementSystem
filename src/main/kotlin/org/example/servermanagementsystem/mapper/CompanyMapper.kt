package org.example.servermanagementsystem.mapper

import org.example.servermanagementsystem.dto.request.CreateCompanyRequestDto
import org.example.servermanagementsystem.dto.response.CompanyResponseDto
import org.example.servermanagementsystem.entity.Company
import org.springframework.stereotype.Component

@Component
class CompanyMapper(
    private val departmentMapper: DepartmentMapper
) {

    fun toEntity(createCompanyRequestDto: CreateCompanyRequestDto): Company =
        Company(
            name = createCompanyRequestDto.name
        )

    fun toDto(company: Company): CompanyResponseDto =
        CompanyResponseDto(
            id = company.id,
            name = company.name,
            createdAt = company.createdAt,
            departments = company.departments.map { departmentMapper.toDto(it) }
        )
}