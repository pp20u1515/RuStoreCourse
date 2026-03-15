package com.example.rustorecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rustorecourse.presentation.ui.AppListScreen
import com.example.rustorecourse.ui.theme.RuStoreCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreCourseTheme {
                AppListScreen()
            }
        }
    }
}