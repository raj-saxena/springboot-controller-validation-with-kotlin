package com.tbst.validation.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.readValue
import com.tbst.validation.controllers.User
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JacksonConfigTest {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should deserialize correctly`() {
        val name = "raj"
        val age = 30
        val json = """ { "name": "$name", "age": $age } """

        val actual = objectMapper.readValue<User>(json)

        assertThat(actual).isEqualTo(User(name, age))
    }

    @Test
    fun `should fail for missing data`() {
        val json = """ { "name": "${"raj"}" } """

        assertThatThrownBy { objectMapper.readValue<User>(json) }
            .isInstanceOf(MismatchedInputException::class.java)
            .hasMessageContaining("Missing required creator property 'age'")
    }
}