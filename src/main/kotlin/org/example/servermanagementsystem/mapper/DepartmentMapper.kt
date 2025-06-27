package org.example.servermanagementsystem.mapper

import org.example.servermanagementsystem.dto.request.CreateDepartmentRequestDto
import org.example.servermanagementsystem.dto.response.DepartmentResponseDto
import org.example.servermanagementsystem.entity.Department
import org.springframework.stereotype.Component

@Component
class DepartmentMapper {
    fun toEntity(createDepartmentRequestDto: CreateDepartmentRequestDto): Department =
        Department(
            name = createDepartmentRequestDto.name
        )

    fun toDto(department: Department): DepartmentResponseDto =
        DepartmentResponseDto(
            id = department.id,
            name = department.name,
            createdAt = department.createdAt,
            companyId = department.company!!.id
        )
}