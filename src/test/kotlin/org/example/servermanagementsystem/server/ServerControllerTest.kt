package org.example.servermanagementsystem.server

import org.example.servermanagementsystem.AbstractTest
import org.example.servermanagementsystem.dto.request.CreateServerRequestDto
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class ServerControllerTest : AbstractTest() {

    @Test
    fun getServersByDepartmentIdTest() {
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

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "  ", "\t", "\n", "m"])
    fun return400IfNameIsNotValidTest(name: String) {
        val payload = """
            {
              "name": "$name",
              "manufacturer": "Dell",
              "ip": "192.168.0.1",
              "ramGb": 16,
              "diskGb": 256,
              "responsibleId": "${testEmployee.id}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/servers") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun validUserNameTest() {
        val payload = """
            {
              "name": "mm",
              "manufacturer": "Dell",
              "ip": "192.168.0.1",
              "ramGb": 16,
              "diskGb": 256,
              "responsibleId": "${testEmployee.id}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/servers") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isOk() }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["2001::85a3::8a2e", "2001:db8:85a3", "12345::abcd", "::1::", "fe80:::1", " ", "192.168.1", "192.168.01.1", "192.168.1.1.1", "192.168.1.", "256.100.100.100"])
    fun return400IfIpIsNotValidTest(ip: String) {
        val payload = """
            {
              "name": "name",
              "manufacturer": "Dell",
              "ip": "$ip",
              "ramGb": 16,
              "diskGb": 256,
              "responsibleId": "${testEmployee.id}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/servers") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0.0.0.0", "255.255.255.255", "172.16.254.3", "10.0.0.255", "2001:0db8:85a3:0000:0000:8a2e:0370:7334", "2001:db8::1", "::1", "fe80::", "0:0:0:0:0:0:0:1"])
    fun validIpTest(ip: String) {
        val payload = """
            {
              "name": "name",
              "manufacturer": "Dell",
              "ip": "$ip",
              "ramGb": 16,
              "diskGb": 256,
              "responsibleId": "${testEmployee.id}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/servers") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isOk() }
        }
    }

}