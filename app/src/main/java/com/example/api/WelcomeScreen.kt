package com.example.api

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(username: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Welcome, $username!", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /* Navigate to location tracking next */ }) {
            Text("Start Location Tracking")
        }
    }
}