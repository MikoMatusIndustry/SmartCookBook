package com.smartcookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.smartcookbook.ui.navigation.NavGraph
import com.smartcookbook.ui.theme.SmartCookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCookBookTheme {
                NavGraph()
            }
        }
    }
}
