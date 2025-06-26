package org.example.servermanagementsystem.mapper

import org.example.servermanagementsystem.dto.response.ServerResponseDto
import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.example.servermanagementsystem.entity.Employee
import org.example.servermanagementsystem.entity.Server
import org.springframework.stereotype.Component

@Component
class ServerMapper {
    fun toDto(entity: Server): ServerResponseDto =
        ServerResponseDto(
            id = entity.id,
            name = entity.name,
            ip = entity.ip,
            ramGb = entity.ramGb,
            diskGb = entity.diskGb,
            manufacturer = entity.manufacturer,
            responsibleId = entity.responsible!!.id
        )

    fun toEntity(dto: CreateServerRequestDto, responsible: Employee): Server =
        Server(
            name = dto.name,
            manufacturer = dto.manufacturer,
            ip = dto.ip,
            ramGb = dto.ramGb,
            diskGb = dto.diskGb,
            responsible = responsible
        )

}