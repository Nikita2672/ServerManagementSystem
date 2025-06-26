package org.example.servermanagementsystem.service

import org.example.servermanagementsystem.dto.request.CreateDepartmentRequestDto
import org.example.servermanagementsystem.dto.request.UpdateDepartmentRequestDto
import org.example.servermanagementsystem.dto.response.DepartmentResponseDto
import java.util.*

interface DepartmentService {

    fun createDepartment(createDepartmentRequestDto: CreateDepartmentRequestDto): DepartmentResponseDto

    fun getDepartmentById(departmentId: UUID): DepartmentResponseDto

    fun updateDepartment(departmentId: UUID, updateDepartmentRequestDto: UpdateDepartmentRequestDto): DepartmentResponseDto

    fun deleteDepartment(departmentId: UUID)
}