package org.example.servermanagementsystem.server

import org.example.servermanagementsystem.AbstractTest
import org.example.servermanagementsystem.dto.request.CreateCompanyRequestDto
import org.example.servermanagementsystem.dto.request.UpdateCompanyRequestDto
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class CompanyControllerTest : AbstractTest() {

    @Test
    fun getCompanyByIdTest() {
        mockMvc.get("/api/v1/company/${testCompany.id}") {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(testCompany.id.toString()) }
                jsonPath("$.name") { value(testCompany.name) }
                jsonPath("$.departments") { hasSize<Any>(1) }
            }
    }

    @Test
    fun createCompanyTest() {
        val request = CreateCompanyRequestDto(
            name = "New Company",
            departments = listOf(),
        )

        mockMvc.post("/api/v1/company") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { exists() }
                jsonPath("$.name") { value("New Company") }
                jsonPath("$.departments") { hasSize<Any>(1) }
            }

        Assertions.assertEquals(2, companyRepository.count())
    }

    @Test
    fun updateCompanyTest() {
        val updateRequest = UpdateCompanyRequestDto(
            name = "Updated Company Name"
        )

        mockMvc.put("/api/v1/company/${testCompany.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(testCompany.id.toString()) }
                jsonPath("$.name") { value("Updated Company Name") }
                jsonPath("$.departments") { hasSize<Any>(testCompany.departments.size) }
            }

        val updated = companyRepository.findById(testCompany.id).get()
        Assertions.assertEquals("Updated Company Name", updated.name)
    }

    @Test
    fun deleteCompanyTest() {
        mockMvc.delete("/api/v1/company/${testCompany.id}")
            .andExpect {
            }

        val exists = companyRepository.existsById(testCompany.id)
        Assertions.assertFalse(exists, "Компания должна быть удалена из базы")

        Assertions.assertEquals(0, companyRepository.count())
    }
}