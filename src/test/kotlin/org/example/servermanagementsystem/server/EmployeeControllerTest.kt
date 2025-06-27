package org.example.servermanagementsystem.server

import org.example.servermanagementsystem.AbstractTest
import org.example.servermanagementsystem.dto.request.CreateEmployeeRequestDto
import org.example.servermanagementsystem.dto.request.UpdateEmployeeRequestDto
import org.junit.jupiter.api.Assertions
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import kotlin.test.Test

class EmployeeControllerTest : AbstractTest() {
    @Test
    fun createEmployeeTest() {
        val request = CreateEmployeeRequestDto(
            name = "Alice",
            email = "alice@example.com",
            position = "QA",
            departmentId = testDepartment.id
        )

        mockMvc.post("/api/v1/employees") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { exists() }
            jsonPath("$.name") { value("Alice") }
            jsonPath("$.email") { value("alice@example.com") }
            jsonPath("$.position") { value("QA") }
        }

        Assertions.assertEquals(2, employeeRepository.count())
    }

    @Test
    fun getEmployeeByIdTest() {
        mockMvc.get("/api/v1/employees/${testEmployee.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(testEmployee.id.toString()) }
                jsonPath("$.name") { value("Test Employee") }
            }
    }

    @Test
    fun getEmployeesByCompanyIdTest() {
        mockMvc.get("/api/v1/employees/by-company/${testCompany.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].id") { value(testEmployee.id.toString()) }
                jsonPath("$[0].name") { value("Test Employee") }
            }
    }

    @Test
    fun updateEmployeeTest() {
        val request = UpdateEmployeeRequestDto(
            name = "John Updated",
            email = "john.updated@example.com",
            position = "Senior Developer",
            departmentId = testDepartment.id
        )

        mockMvc.put("/api/v1/employees/${testEmployee.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(testEmployee.id.toString()) }
            jsonPath("$.name") { value("John Updated") }
            jsonPath("$.email") { value("john.updated@example.com") }
            jsonPath("$.position") { value("Senior Developer") }
        }

        val updated = employeeRepository.findById(testEmployee.id).get()
        Assertions.assertEquals("John Updated", updated.name)
    }

    @Test
    fun deleteEmployeeTest() {
        mockMvc.delete("/api/v1/employees/${testEmployee.id}")
            .andExpect {
                status { isNoContent() }
            }

        Assertions.assertFalse(employeeRepository.existsById(testEmployee.id))
    }
}