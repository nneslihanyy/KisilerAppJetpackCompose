package com.example.kisilerlistesiuygulamasi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kisilerlistesiuygulamasi.ui.components.UserItem
import androidx.compose.material.icons.filled.Search
import com.example.kisilerlistesiuygulamasi.viewmodel.UserUiState
import com.example.kisilerlistesiuygulamasi.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel,
    onUserClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                TopAppBar(
                    title = { Text("Kullanıcı Listesi") }
                )
                OutlinedTextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("İsim veya email ara...") },
                    leadingIcon = {
                        Icon(androidx.compose.material.icons.Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is UserUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UserUiState.Success -> {
                    LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                        items(state.users) { user ->
                            UserItem(
                                user = user,
                                onClick = { onUserClick(user.id) }
                            )
                        }
                    }
                }
                is UserUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { viewModel.getUsers() }) {
                            Text("Tekrar Dene")
                        }
                    }
                }
            }
        }
    }
}