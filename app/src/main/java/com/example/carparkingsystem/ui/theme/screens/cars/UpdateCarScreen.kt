package com.example.carparkingsystem.ui.theme.screens.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.carparkingsystem.data.CarViewModel
import com.example.carparkingsystem.models.CarModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCarScreen(navController: NavController, carId: String) {
    val context = LocalContext.current
    val carViewModel: CarViewModel = viewModel()
    
    var plateNumber by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var driverName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var entryTime by remember { mutableStateOf("") }

    // Load car details
    val cars = carViewModel.cars
    val car = cars.find { it.id == carId }
    
    LaunchedEffect(car) {
        car?.let {
            plateNumber = it.plate_number
            vehicleType = it.vehicle_type
            driverName = it.driver_name
            phoneNumber = it.phone_number
            color = it.color
            entryTime = it.entry_time
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Vehicle Details", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Car Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = plateNumber,
                onValueChange = { plateNumber = it },
                label = { Text("Plate Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = vehicleType,
                onValueChange = { vehicleType = it },
                label = { Text("Vehicle Type") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = driverName,
                onValueChange = { driverName = it },
                label = { Text("Driver Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = entryTime,
                onValueChange = { entryTime = it },
                label = { Text("Entry Time") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    carViewModel.updateCar(
                        id = carId,
                        plate_number = plateNumber,
                        vehicle_type = vehicleType,
                        driver_name = driverName,
                        phone_number = phoneNumber,
                        color = color,
                        entry_time = entryTime,
                        context = context,
                        navController = navController
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Car")
            }
        }
    }
}
