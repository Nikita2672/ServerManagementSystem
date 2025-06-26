package org.example.servermanagementsystem.repository

import org.example.servermanagementsystem.entity.Server
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServerRepository : JpaRepository<Server, UUID> {
    fun findAllByResponsible_Department_Id(departmentId: UUID): List<Server>
}