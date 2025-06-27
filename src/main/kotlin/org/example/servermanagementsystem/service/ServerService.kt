package org.example.servermanagementsystem.service

import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.example.servermanagementsystem.dto.request.UpdateServerRequestDto
import org.example.servermanagementsystem.dto.response.ServerResponseDto
import java.util.*

interface ServerService {

    fun createServer(createServerRequestDto: CreateServerRequestDto): ServerResponseDto

    fun findServersByDepartment(departmentId: UUID): List<ServerResponseDto>

    fun updateServer(serverId: UUID, updateServerRequestDto: UpdateServerRequestDto): ServerResponseDto

    fun deleteServer(serverId: UUID)
}