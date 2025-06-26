package org.example.servermanagementsystem.repository

import org.example.servermanagementsystem.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CompanyRepository : JpaRepository<Company, UUID>