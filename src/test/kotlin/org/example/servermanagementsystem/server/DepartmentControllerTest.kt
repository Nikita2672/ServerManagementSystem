package org.example.servermanagementsystem.server

import org.example.servermanagementsystem.AbstractTest
import org.example.servermanagementsystem.dto.request.CreateDepartmentRequestDto
import org.example.servermanagementsystem.dto.request.UpdateDepartmentRequestDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class DepartmentControllerTest : AbstractTest() {
    @Test
    fun createDepartmentTest() {
        val request = CreateDepartmentRequestDto(
            name = "Finance Department",
            companyId = testCompany.id
        )

        mockMvc.post("/api/v1/departments") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { exists() }
                jsonPath("$.name") { value("Finance Department") }
                jsonPath("$.companyId") { value(testCompany.id.toString()) }
            }

        Assertions.assertEquals(2, departmentRepository.count())
    }

    @Test
    fun getDepartmentByIdTest() {
        mockMvc.get("/api/v1/departments/${testDepartment.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(testDepartment.id.toString()) }
                jsonPath("$.name") { value("Test Department") }
                jsonPath("$.companyId") { value(testCompany.id.toString()) }
            }
    }

    @Test
    fun updateDepartmentTest() {
        val request = UpdateDepartmentRequestDto(
            name = "Updated Department",
            companyId = testCompany.id
        )

        mockMvc.put("/api/v1/departments/${testDepartment.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(testDepartment.id.toString()) }
                jsonPath("$.name") { value("Updated Department") }
            }

        val updated = departmentRepository.findById(testDepartment.id).get()
        Assertions.assertEquals("Updated Department", updated.name)
    }

    @Test
    fun deleteDepartmentTest() {
        mockMvc.delete("/api/v1/departments/${testDepartment.id}")
            .andExpect {
                status { isNoContent() }
            }

        val exists = departmentRepository.existsById(testDepartment.id)
        Assertions.assertFalse(exists)
    }
}