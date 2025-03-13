package com.example.api

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
@Composable
fun WelcomeScreen(username: String) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationText by remember { mutableStateOf("Fetching location...") }

    // Function to get location
    val getLocation = {
        val locationRequest = LocationRequest.create().apply {
            interval = 15000 // 15 seconds
            fastestInterval = 15000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    locationText = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    // Start fetching location when screen loads
    LaunchedEffect(Unit) {
        getLocation()
    }

    // UI Layout
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Welcome, $username!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = locationText, style = MaterialTheme.typography.bodyLarge)
    }
}
