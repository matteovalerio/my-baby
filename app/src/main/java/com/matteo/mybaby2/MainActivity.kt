package com.matteo.mybaby2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.matteo.mybaby2.common.navigations.Navigation
import com.matteo.mybaby2.ui.theme.MyBaby2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBaby2Theme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}