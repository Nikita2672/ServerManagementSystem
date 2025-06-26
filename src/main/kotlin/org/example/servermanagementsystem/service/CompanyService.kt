package org.example.servermanagementsystem.service

import org.example.servermanagementsystem.dto.request.CreateCompanyRequestDto
import org.example.servermanagementsystem.dto.request.UpdateCompanyRequestDto
import org.example.servermanagementsystem.dto.response.CompanyResponseDto
import java.util.*

interface CompanyService {

    fun createCompany(createCompanyRequestDto: CreateCompanyRequestDto): CompanyResponseDto

    fun getCompany(companyId: UUID): CompanyResponseDto

    fun updateCompany(companyId: UUID, updateCompanyRequestDto: UpdateCompanyRequestDto): CompanyResponseDto

    fun deleteCompany(companyId: UUID)
}