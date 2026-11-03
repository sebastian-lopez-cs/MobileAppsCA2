
package com.example.mobileappsca2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    val normalCount = remember { mutableStateOf(0) }
    val savedCount = rememberSaveable { mutableStateOf(0) }

    CounterButton(
        normalCount = normalCount.value,
        savedCount = savedCount.value,
        onIncrement = {
            normalCount.value++
            savedCount.value++
        },
        modifier = modifier
    )
}

@Composable
fun CounterButton(
    normalCount: Int,
    savedCount: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Normal Counter: $normalCount")
        Text(text = "Saved Counter: $savedCount")

        Button(onClick = onIncrement) {
            Text(text = "Increment Both")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    MobileAppsCA2Theme {
        CounterScreen()
    }
}

