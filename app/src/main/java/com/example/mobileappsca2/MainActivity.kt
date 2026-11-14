
package com.example.mobileappsca2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileappsca2.ui.theme.MobileAppsCA2Theme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobileAppsCA2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuthenticationScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Firebase authentication
@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier) {

    val auth = remember { Firebase.auth }

    var isLoggedIn by remember {
        mutableStateOf(auth.currentUser != null)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    if (isLoggedIn) {

        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {

            Text(text = "Logged in: ${auth.currentUser?.email}")

            CounterScreen()

            // Exceptions 1
            ExceptionsOneScreen()

            Button(onClick = {
                auth.signOut()
                isLoggedIn = false
                password = ""
                message = ""
            }) {
                Text("Logout")
            }
        }

    } else {

        Column(modifier = modifier) {

            Text(text = "Firebase Authentication")

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(onClick = {

                if (email.isBlank() || password.isBlank()) {
                    message = "Enter email and password"
                } else {

                    auth.signInWithEmailAndPassword(
                        email.trim(),
                        password
                    ).addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            isLoggedIn = auth.currentUser != null
                            password = ""
                            message = ""
                        } else {
                            message = "Authentication failed"
                        }
                    }
                }

            }) {
                Text("Sign In")
            }

            Button(onClick = {

                if (email.isBlank() || password.isBlank()) {
                    message = "Enter email and password"
                } else {

                    auth.createUserWithEmailAndPassword(
                        email.trim(),
                        password
                    ).addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            isLoggedIn = auth.currentUser != null
                            password = ""
                            message = ""
                        } else {
                            message = "Registration failed"
                        }
                    }
                }

            }) {
                Text("Register")
            }

            Text(text = message)
        }
    }
}

// State Management: stateful parent
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

// State Management: stateless child
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

// Exceptions 1: Precondition functions
@Composable
fun ExceptionsOneScreen() {

    var isLightOn by remember { mutableStateOf(false) }
    var numberInput by remember { mutableStateOf("") }

    var lightResult by remember { mutableStateOf("") }
    var numberResult by remember { mutableStateOf("") }

    Column {

        Text(text = "Exceptions 1")

        // Scenario A: Light is not turned on
        Text(text = "Light status")

        Checkbox(
            checked = isLightOn,
            onCheckedChange = { isLightOn = it }
        )

        Text(
            text = if (isLightOn) "Light ON" else "Light OFF"
        )

        Button(onClick = {

            try {
                check(isLightOn) { "Light is off" }
                lightResult = "Light check passed"

            } catch (e: IllegalStateException) {
                lightResult = e.message ?: "Light check failed"
            }

        }) {
            Text("Check Light")
        }

        Text(text = lightResult)

        // Scenario B: Number cannot be negative
        Text(text = "Enter a non-negative number")

        TextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Number") }
        )

        Button(onClick = {

            try {
                val number = numberInput.toIntOrNull()

                require(number != null && number >= 0) {
                    "Enter a non-negative whole number"
                }

                numberResult = "Number accepted: $number"

            } catch (e: IllegalArgumentException) {
                numberResult = e.message ?: "Invalid number"
            }

        }) {
            Text("Check Number")
        }

        Text(text = numberResult)
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    MobileAppsCA2Theme {
        CounterScreen()
    }
}
