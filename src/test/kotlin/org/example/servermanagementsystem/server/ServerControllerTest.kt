package org.example.servermanagementsystem.server

import org.example.servermanagementsystem.AbstractTest
import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.example.servermanagementsystem.entity.Company
import org.example.servermanagementsystem.entity.Department
import org.example.servermanagementsystem.entity.Employee
import org.example.servermanagementsystem.entity.Server
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.util.*

class ServerControllerTest : AbstractTest() {

    protected lateinit var testCompany: Company
    protected lateinit var testDepartment: Department
    protected lateinit var testEmployee: Employee
    protected lateinit var testServer: Server

    @BeforeEach
    fun setUp() {
        // Company
        testCompany = Company(
            id = UUID.randomUUID(),
            name = "Test Company"
        )
        companyRepository.save(testCompany)

        // Department
        testDepartment = Department(
            id = UUID.randomUUID(),
            name = "Test Department",
            company = testCompany
        )
        departmentRepository.save(testDepartment)

        // Employee
        testEmployee = Employee(
            id = UUID.randomUUID(),
            name = "Test Employee",
            email = "test@example.com",
            position = "Engineer",
            department = testDepartment
        )
        employeeRepository.save(testEmployee)

        // Server
        testServer = Server(
            id = UUID.randomUUID(),
            name = "Test Server",
            ip = "192.168.1.100",
            ramGb = 16,
            diskGb = 256,
            manufacturer = "Dell",
            responsible = testEmployee
        )
        serverRepository.save(testServer)
    }

    @Test
    fun getServersByDepartmentId() {
        mockMvc.get("/api/v1/servers/by-department/${testDepartment.id}") {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$", hasSize<Any>(1))
                jsonPath("$[0].id") { value(testServer.id.toString()) }
                jsonPath("$[0].name") { value(testServer.name) }
                jsonPath("$[0].ip") { value(testServer.ip) }
            }
    }

    @Test
    fun createServerTest() {
        val request = CreateServerRequestDto(
            name = "New Server",
            ip = "10.0.0.1",
            ramGb = 32,
            diskGb = 512,
            manufacturer = "HP",
            responsibleId = testEmployee.id
        )

        mockMvc.post("/api/v1/servers") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { exists() }
                jsonPath("$.name") { value("New Server") }
                jsonPath("$.ip") { value("10.0.0.1") }
                jsonPath("$.ramGb") { value(32) }
                jsonPath("$.diskGb") { value(512) }
                jsonPath("$.manufacturer") { value("HP") }
            }

        Assertions.assertEquals(2, serverRepository.count())
    }

    @Test
    fun updateServerTest() {
        val request = CreateServerRequestDto(
            name = "Updated Server",
            ip = "10.0.0.99",
            ramGb = 64,
            diskGb = 1024,
            manufacturer = "Supermicro",
            responsibleId = testEmployee.id
        )

        mockMvc.put("/api/v1/servers/${testServer.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(testServer.id.toString()) }
                jsonPath("$.name") { value("Updated Server") }
                jsonPath("$.ip") { value("10.0.0.99") }
                jsonPath("$.ramGb") { value(64) }
                jsonPath("$.diskGb") { value(1024) }
                jsonPath("$.manufacturer") { value("Supermicro") }
            }

        val updated = serverRepository.findById(testServer.id).get()

        Assertions.assertEquals("Updated Server", updated.name)
        Assertions.assertEquals("10.0.0.99", updated.ip)
        Assertions.assertEquals(64, updated.ramGb)
        Assertions.assertEquals(1024, updated.diskGb)
        Assertions.assertEquals("Supermicro", updated.manufacturer)
        Assertions.assertEquals(1, serverRepository.count())
    }

    @Test
    fun deleteServerTest() {
        mockMvc.delete("/api/v1/servers/${testServer.id}")
            .andExpect {
                status { isNoContent() }
            }

        val exists = serverRepository.existsById(testServer.id)
        Assertions.assertFalse(exists, "Сервер должен быть удалён из базы")

        Assertions.assertEquals(0, serverRepository.count(), "Серверов в базе больше быть не должно")
    }
}