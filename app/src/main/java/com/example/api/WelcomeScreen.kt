package com.example.api


import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun WelcomeScreen(token: String) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val scope = rememberCoroutineScope()
    var locationText by remember { mutableStateOf("Fetching location...") }

    val getLocation = {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 15000) // ✅ Fixed `Builder`
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val lat = location.latitude
                    val lng = location.longitude
                    locationText = "Lat: $lat, Lng: $lng"

                    scope.launch {
                        RetrofitInstance.api.sendLocation(LocationRequest(token, lat, lng)) // ✅ Ensure API is correct
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    LaunchedEffect(Unit) { getLocation() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = locationText)
    }
}

// ✅ Custom data class for sending location to backend
data class LocationData(val token: String, val lat: Double, val lng: Double)
