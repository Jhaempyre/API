package com.example.api

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                val response = RetrofitInstance.api.login(LoginRequest(email.value, password.value))
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: return@launch
                    navController.navigate("welcome/$token")
                } else {
                    Log.e("LoginScreen", "Login failed")
                }
            }
        }) {
            Text("Login")
        }
    }
}
