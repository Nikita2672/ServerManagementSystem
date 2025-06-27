package org.example.servermanagementsystem.service.impl

import org.example.servermanagementsystem.dto.request.CreateCompanyRequestDto
import org.example.servermanagementsystem.dto.request.UpdateCompanyRequestDto
import org.example.servermanagementsystem.dto.response.CompanyResponseDto
import org.example.servermanagementsystem.exception.ResourceNotFoundException
import org.example.servermanagementsystem.mapper.CompanyMapper
import org.example.servermanagementsystem.repository.CompanyRepository
import org.example.servermanagementsystem.service.CompanyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val companyMapper: CompanyMapper
) : CompanyService {

    @Transactional
    override fun createCompany(createCompanyRequestDto: CreateCompanyRequestDto): CompanyResponseDto {
        val company = companyMapper.toEntity(createCompanyRequestDto)
        val savedCompany = companyRepository.save(company)
        return companyMapper.toDto(savedCompany)
    }

    @Transactional
    override fun getCompany(companyId: UUID): CompanyResponseDto {
        val company = companyRepository.findById(companyId)
            .orElseThrow { ResourceNotFoundException("company with id $companyId not found") }
        return companyMapper.toDto(company)
    }

    @Transactional
    override fun updateCompany(companyId: UUID, updateCompanyRequestDto: UpdateCompanyRequestDto): CompanyResponseDto {
        val company = companyRepository.findById(companyId)
            .orElseThrow { ResourceNotFoundException("company with id $companyId not found") }
        company.name = updateCompanyRequestDto.name
        val savedCompany = companyRepository.save(company)
        return companyMapper.toDto(savedCompany)
    }

    @Transactional
    override fun deleteCompany(companyId: UUID) {
        val company = companyRepository.findById(companyId)
            .orElseThrow { ResourceNotFoundException("company with id $companyId not found") }
        companyRepository.delete(company)
    }
}