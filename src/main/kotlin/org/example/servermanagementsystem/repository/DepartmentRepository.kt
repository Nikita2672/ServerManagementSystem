package org.example.servermanagementsystem.repository

import org.example.servermanagementsystem.entity.Department
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DepartmentRepository : JpaRepository<Department, UUID>