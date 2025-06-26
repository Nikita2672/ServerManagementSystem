package org.example.servermanagementsystem.service

import org.example.servermanagementsystem.dto.request.CreateEmployeeRequestDto
import org.example.servermanagementsystem.dto.request.UpdateEmployeeRequestDto
import org.example.servermanagementsystem.dto.response.EmployeeResponseDto
import java.util.*

interface EmployeeService {

    fun createEmployee(createEmployeeRequestDto: CreateEmployeeRequestDto): EmployeeResponseDto

    fun findEmployeeById(employeeId: UUID): EmployeeResponseDto

    fun findEmployeesByCompanyId(companyId: UUID): List<EmployeeResponseDto>

    fun updateEmployee(employeeId: UUID, updateEmployeeRequestDto: UpdateEmployeeRequestDto): EmployeeResponseDto

    fun deleteEmployee(employeeId: UUID)
}