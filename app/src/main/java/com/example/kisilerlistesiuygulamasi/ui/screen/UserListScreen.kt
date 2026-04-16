package com.example.kisilerlistesiuygulamasi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisilerlistesiuygulamasi.ui.components.UserItem
import com.example.kisilerlistesiuygulamasi.ui.viewmodel.UserUiState
import com.example.kisilerlistesiuygulamasi.ui.viewmodel.UserViewModel

@Composable
fun UserListScreen(viewModel: UserViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Kullanıcı Listesi") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {
                is UserUiState.Loading -> {
                    // Yükleniyor durumu [cite: 39]
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UserUiState.Success -> {
                    // Liste gösterme [cite: 35]
                    LazyColumn {
                        items(state.users) { user ->
                            UserItem(user = user)
                        }
                    }
                }
                is UserUiState.Error -> {
                    // Hata durumu ve Tekrar Dene butonu [cite: 40]
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.getUsers() }) {
                            Text("Tekrar Dene")
                        }
                    }
                }
            }
        }
    }
}