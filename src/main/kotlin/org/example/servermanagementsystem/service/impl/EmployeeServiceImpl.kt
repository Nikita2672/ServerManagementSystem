package org.example.servermanagementsystem.service.impl

import org.example.servermanagementsystem.dto.request.CreateEmployeeRequestDto
import org.example.servermanagementsystem.dto.request.UpdateEmployeeRequestDto
import org.example.servermanagementsystem.dto.response.EmployeeResponseDto
import org.example.servermanagementsystem.exception.ResourceNotFoundException
import org.example.servermanagementsystem.mapper.EmployeeMapper
import org.example.servermanagementsystem.repository.DepartmentRepository
import org.example.servermanagementsystem.repository.EmployeeRepository
import org.example.servermanagementsystem.service.EmployeeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EmployeeServiceImpl(
    private val employeeMapper: EmployeeMapper,
    private val employeeRepository: EmployeeRepository,
    private val departmentRepository: DepartmentRepository
) : EmployeeService {

    @Transactional
    override fun createEmployee(createEmployeeRequestDto: CreateEmployeeRequestDto): EmployeeResponseDto {
        val department = departmentRepository.findById(createEmployeeRequestDto.departmentId)
            .orElseThrow { ResourceNotFoundException("Department with id ${createEmployeeRequestDto.departmentId} not found") }
        val employee = employeeMapper.toEntity(createEmployeeRequestDto, department)
        val savedEmployee = employeeRepository.save(employee)
        return employeeMapper.toDto(savedEmployee)
    }

    override fun findEmployeeById(employeeId: UUID): EmployeeResponseDto {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee with id $employeeId not found") }
        return employeeMapper.toDto(employee)
    }

    override fun findEmployeesByCompanyId(companyId: UUID): List<EmployeeResponseDto> {
        val employees = employeeRepository.findAllByDepartment_Company_Id(companyId)
        return employees.map { employeeMapper.toDto(it) }
    }

    @Transactional
    override fun updateEmployee(employeeId: UUID, updateEmployeeRequestDto: UpdateEmployeeRequestDto): EmployeeResponseDto {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee with id $employeeId not found") }
        val department = departmentRepository.findById(updateEmployeeRequestDto.departmentId)
            .orElseThrow { ResourceNotFoundException("Department with id ${updateEmployeeRequestDto.departmentId} not found") }

        employee.name = updateEmployeeRequestDto.name
        employee.email = updateEmployeeRequestDto.email
        employee.position = updateEmployeeRequestDto.position
        employee.department = department

        val updatedEmployee = employeeRepository.save(employee)
        return employeeMapper.toDto(updatedEmployee)
    }

    @Transactional
    override fun deleteEmployee(employeeId: UUID) {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee with id $employeeId not found") }
        employeeRepository.delete(employee)
    }
}