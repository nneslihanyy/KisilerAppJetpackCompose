package com.example.kisilerlistesiuygulamasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kisilerlistesiuygulamasi.ui.screen.UserDetailScreen
import com.example.kisilerlistesiuygulamasi.ui.screen.UserListScreen
import com.example.kisilerlistesiuygulamasi.ui.theme.KisilerListesiUygulamasiTheme
import com.example.kisilerlistesiuygulamasi.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KisilerListesiUygulamasiTheme {
                val navController = rememberNavController()
                val viewModel: UserViewModel = viewModel()
                
                NavHost(navController = navController, startDestination = "user_list") {
                    // 1. Ekran: Kullanıcı Listesi
                    composable("user_list") {
                        UserListScreen(
                            viewModel = viewModel,
                            onUserClick = { userId ->
                                navController.navigate("user_detail/$userId")
                            }
                        )
                    }
                    
                    // 2. Ekran: Kullanıcı Detayı
                    composable(
                        route = "user_detail/{userId}",
                        arguments = listOf(navArgument("userId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
                        val user = viewModel.getUserById(userId)
                        
                        if (user != null) {
                            UserDetailScreen(
                                user = user,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}