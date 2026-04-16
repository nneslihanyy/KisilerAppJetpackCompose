package com.example.kisilerlistesiuygulamasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kisilerlistesiuygulamasi.ui.screen.UserListScreen
import com.example.kisilerlistesiuygulamasi.ui.theme.KisilerListesiUygulamasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KisilerListesiUygulamasiTheme {
                // Hazırladığımız ana ekranı burada çağırıyoruz
                UserListScreen()
            }
        }
    }
}