package org.example.servermanagementsystem

import org.example.servermanagementsystem.entity.Company
import org.example.servermanagementsystem.entity.Department
import org.example.servermanagementsystem.entity.Employee
import org.example.servermanagementsystem.entity.Server
import org.example.servermanagementsystem.repository.CompanyRepository
import org.example.servermanagementsystem.repository.DepartmentRepository
import org.example.servermanagementsystem.repository.EmployeeRepository
import org.example.servermanagementsystem.repository.ServerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class AbstractTest {

    companion object {
        @ServiceConnection
        val postgres = PostgreSQLContainer<Nothing>("postgres:15-alpine")

        init {
            Startables.deepStart(postgres)
        }
    }

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

    @AfterEach
    fun clear() {
        employeeRepository.deleteAll()
        companyRepository.deleteAll()
        serverRepository.deleteAll()
        departmentRepository.deleteAll()
    }

    protected var objectMapper = ObjectMapper()

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var companyRepository: CompanyRepository

    @Autowired
    protected lateinit var serverRepository: ServerRepository

    @Autowired
    protected lateinit var employeeRepository: EmployeeRepository

    @Autowired
    protected lateinit var departmentRepository: DepartmentRepository
}