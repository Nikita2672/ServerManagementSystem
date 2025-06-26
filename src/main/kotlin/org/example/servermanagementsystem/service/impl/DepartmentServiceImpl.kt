package org.example.servermanagementsystem.service.impl

import org.example.servermanagementsystem.dto.request.CreateDepartmentRequestDto
import org.example.servermanagementsystem.dto.request.UpdateDepartmentRequestDto
import org.example.servermanagementsystem.dto.response.DepartmentResponseDto
import org.example.servermanagementsystem.exception.ResourceNotFoundException
import org.example.servermanagementsystem.mapper.DepartmentMapper
import org.example.servermanagementsystem.repository.CompanyRepository
import org.example.servermanagementsystem.repository.DepartmentRepository
import org.example.servermanagementsystem.service.DepartmentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DepartmentServiceImpl(
    private val departmentRepository: DepartmentRepository,
    private val departmentMapper: DepartmentMapper,
    private val companyRepository: CompanyRepository
) : DepartmentService {

    @Transactional
    override fun createDepartment(createDepartmentRequestDto: CreateDepartmentRequestDto): DepartmentResponseDto {
        val department = departmentMapper.toEntity(createDepartmentRequestDto)
        val savedDepartment = departmentRepository.save(department)
        return departmentMapper.toDto(savedDepartment)
    }

    override fun getDepartmentById(departmentId: UUID): DepartmentResponseDto {
        val department = departmentRepository.findById(departmentId)
            .orElseThrow { ResourceNotFoundException("Department with id $departmentId not found") }
        return departmentMapper.toDto(department)
    }

    @Transactional
    override fun updateDepartment(departmentId: UUID, updateDepartmentRequestDto: UpdateDepartmentRequestDto): DepartmentResponseDto {
        val department = departmentRepository.findById(departmentId)
            .orElseThrow { ResourceNotFoundException("Department with id $departmentId not found") }
        val company = companyRepository.findById(updateDepartmentRequestDto.companyId)
            .orElseThrow { ResourceNotFoundException("Company with id ${updateDepartmentRequestDto.companyId} not found") }

        department.name = updateDepartmentRequestDto.name
        department.company = company

        val updatedDepartment = departmentRepository.save(department)
        return departmentMapper.toDto(updatedDepartment)
    }

    @Transactional
    override fun deleteDepartment(departmentId: UUID) {
        if (!departmentRepository.existsById(departmentId)) {
            throw ResourceNotFoundException("Department with id $departmentId not found")
        }
        departmentRepository.deleteById(departmentId)
    }
}