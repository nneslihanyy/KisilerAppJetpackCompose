package com.example.kisilerlistesiuygulamasi.data.repository

import com.example.kisilerlistesiuygulamasi.data.model.User
import com.example.kisilerlistesiuygulamasi.data.remote.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers(): List<User> = apiService.getUsers()
}