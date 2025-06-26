package org.example.servermanagementsystem.service.impl

import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.example.servermanagementsystem.dto.response.ServerResponseDto
import org.example.servermanagementsystem.exception.ResourceNotFoundException
import org.example.servermanagementsystem.mapper.ServerMapper
import org.example.servermanagementsystem.repository.EmployeeRepository
import org.example.servermanagementsystem.repository.ServerRepository
import org.example.servermanagementsystem.service.ServerService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ServerServiceImpl(
    private val serverMapper: ServerMapper,
    private val serverRepository: ServerRepository,
    private val employeeRepository: EmployeeRepository
) : ServerService {

    @Transactional
    override fun createServer(createServerRequestDto: CreateServerRequestDto): ServerResponseDto {
        val employee = employeeRepository.findById(createServerRequestDto.responsibleId)
            .orElseThrow { ResourceNotFoundException("Employee not found") }
        val server = serverMapper.toEntity(createServerRequestDto, employee)
        val savedServer = serverRepository.save(server)
        return serverMapper.toDto(savedServer)
    }

    override fun findServersByDepartment(departmentId: UUID): List<ServerResponseDto> {
        val servers = serverRepository.findAllByResponsible_Department_Id(departmentId)
        return servers.map { serverMapper.toDto(it) }
    }

    @Transactional
    override fun updateServer(serverId: UUID, createServerRequestDto: CreateServerRequestDto): ServerResponseDto {
        val server = serverRepository.findById(serverId)
            .orElseThrow { ResourceNotFoundException("Server not found") }
        val employee = employeeRepository.findById(createServerRequestDto.responsibleId)
            .orElseThrow { ResourceNotFoundException("Employee not found") }

        server.name = createServerRequestDto.name
        server.manufacturer = createServerRequestDto.manufacturer
        server.ip = createServerRequestDto.ip
        server.ramGb = createServerRequestDto.ramGb
        server.diskGb = createServerRequestDto.diskGb
        server.responsible = employee

        val updatedServer = serverRepository.save(server)
        return serverMapper.toDto(updatedServer)
    }

    @Transactional
    override fun deleteServer(serverId: UUID) {
        if (!serverRepository.existsById(serverId)) {
            throw ResourceNotFoundException("Server not found")
        }
        serverRepository.deleteById(serverId)
    }
}