
package com.example.mobileappsca2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileappsca2.ui.theme.MobileAppsCA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppsCA2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterScreen(name: String, modifier: Modifier = Modifier) {
    val normalCount = remember { mutableStateOf(0) }
    val savedCount = rememberSaveable { mutableStateOf(0) }

    Column(modifier = modifier) {
        Text(text = "Hello $name!")
        Text(text = "Normal Counter: ${normalCount.value}")
        Text(text = "Saved Counter: ${savedCount.value}")
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    MobileAppsCA2Theme {
        CounterScreen("Android")
    }
}

