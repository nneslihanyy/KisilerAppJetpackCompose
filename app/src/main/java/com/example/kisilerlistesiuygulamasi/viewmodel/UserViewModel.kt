package com.example.kisilerlistesiuygulamasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisilerlistesiuygulamasi.data.model.User
import com.example.kisilerlistesiuygulamasi.data.remote.RetrofitInstance
import com.example.kisilerlistesiuygulamasi.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}

class UserViewModel : ViewModel() {
    private val repository = UserRepository(RetrofitInstance.api)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)


    val uiState: StateFlow<UserUiState> = _searchText
        .combine(_uiState) { text, state ->
            if (state is UserUiState.Success && text.isNotBlank()) {
                UserUiState.Success(state.users.filter {
                    it.name.contains(text, ignoreCase = true) ||
                            it.email.contains(text, ignoreCase = true)
                })
            } else {
                state
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserUiState.Loading)

    init {
        getUsers()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val users = repository.getUsers()
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }

    fun getUserById(id: Int): User? {
        val currentState = _uiState.value
        return if (currentState is UserUiState.Success) {
            currentState.users.find { it.id == id }
        } else {
            null
        }
    }
}