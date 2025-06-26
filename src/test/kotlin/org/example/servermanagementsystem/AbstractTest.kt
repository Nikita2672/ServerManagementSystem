package org.example.servermanagementsystem

import org.example.servermanagementsystem.repository.CompanyRepository
import org.example.servermanagementsystem.repository.DepartmentRepository
import org.example.servermanagementsystem.repository.EmployeeRepository
import org.example.servermanagementsystem.repository.ServerRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
abstract class AbstractTest {

    companion object {
        @Container
        val postgres = PostgreSQLContainer<Nothing>("postgres:15-alpine").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
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