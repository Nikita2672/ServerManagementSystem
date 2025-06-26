package org.example.servermanagementsystem.mapper

import org.example.servermanagementsystem.dto.request.CreateEmployeeRequestDto
import org.example.servermanagementsystem.dto.response.EmployeeResponseDto
import org.example.servermanagementsystem.entity.Department
import org.example.servermanagementsystem.entity.Employee
import org.springframework.stereotype.Component

@Component
class EmployeeMapper {

    fun toDto(entity: Employee): EmployeeResponseDto =
        EmployeeResponseDto(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            position = entity.position
        )

    fun toEntity(createEmployeeRequestDto: CreateEmployeeRequestDto, department: Department): Employee =
        Employee(
            name = createEmployeeRequestDto.name,
            email = createEmployeeRequestDto.email,
            position = createEmployeeRequestDto.position,
            department = department
        )
}