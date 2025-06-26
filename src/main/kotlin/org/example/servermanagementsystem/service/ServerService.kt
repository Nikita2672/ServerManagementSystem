package org.example.servermanagementsystem.service

import org.example.servermanagementsystem.dto.response.ServerResponseDto
import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import java.util.UUID

interface ServerService {

    fun createServer(createServerRequestDto: CreateServerRequestDto): ServerResponseDto

    fun findServersByDepartment(departmentId: UUID): List<ServerResponseDto>

    fun updateServer(serverId: UUID, createServerRequestDto: CreateServerRequestDto): ServerResponseDto

    fun deleteServer(serverId: UUID)
}