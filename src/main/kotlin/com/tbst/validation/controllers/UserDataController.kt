package com.tbst.validation.controllers

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserDataController {

    @PostMapping("api/user")
    @ResponseStatus(CREATED)
    fun save(@RequestBody user: User) {
    }
}

data class User(val name: String, val age: Int)