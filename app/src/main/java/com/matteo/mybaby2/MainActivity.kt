package com.matteo.mybaby2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.matteo.mybaby2.modules.babies.components.Babies
import com.matteo.mybaby2.ui.theme.MyBaby2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBaby2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Babies(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}