package com.tbst.validation.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tbst.validation.controllers.User
import com.tbst.validation.controllers.UserDataController
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserDataController::class])
class UserDataControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `should accept successful user payload`() {
        val user = User("name", 3)

        mockMvc.perform(
            post("/api/user")
                .content(mapper.writeValueAsString(user))
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andReturn()
    }

    @ParameterizedTest
    @ValueSource(strings = ["{ }", """{ "name": "foo" }""", """{ "age": 3 }"""])
    fun `should reject bad user payload with 400 BadRequest`(user: String) {
        mockMvc.perform(
            post("/api/user")
                .content(user)
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()
    }
}