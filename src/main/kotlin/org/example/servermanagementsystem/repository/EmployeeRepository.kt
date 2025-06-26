package org.example.servermanagementsystem.repository

import org.example.servermanagementsystem.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmployeeRepository : JpaRepository<Employee, UUID> {
    fun findAllByDepartment_Company_Id(companyId: UUID): List<Employee>
}