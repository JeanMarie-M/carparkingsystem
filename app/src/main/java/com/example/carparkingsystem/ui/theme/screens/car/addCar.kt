package com.example.carparkingsystem.ui.theme.screens.car

import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.carparkingsystem.data.AuthViewModel
import com.example.carparkingsystem.data.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(navController: NavController) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    var plate_number by remember { mutableStateOf("") }
    var vehicle_type by remember { mutableStateOf("") }
    var driver_name by remember { mutableStateOf("") }
    var phone_number by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var entry_time by remember { mutableStateOf("") }


    val carViewModel: CarViewModel = viewModel()


    Scaffold(topBar = { TopAppBar(title = { Text("Add Car Details") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White)
    )

    }) {
        padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


            ) {
            Box(modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ){
                if (imageUri.value != null){
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                    )
                }
            }
           
            Button(onClick = { launcher.launch("Image/") },
                modifier = Modifier.align(Alignment.CenterHorizontally)

            ) {
                Text(text = "Select Image")
            }
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = plate_number,
                onValueChange = { plate_number = it },
                label = { Text("Plate Number") },
                placeholder = { Text("Enter your plate number") },
                leadingIcon = { Icon(Icons.Default.Pin, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = vehicle_type,
                onValueChange = { vehicle_type = it },
                label = { Text("Vehicle Type") },
                placeholder = { Text("Enter your vehicle type") },
                leadingIcon = { Icon(Icons.Default.TimeToLeave, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            Spacer(modifier = Modifier.height(24.dp))


            OutlinedTextField(
                value = driver_name,
                onValueChange = { driver_name = it },
                label = { Text("Driver Name") },
                placeholder = { Text("Enter your driver name") },
                leadingIcon = { Icon(Icons.Default.NoteAlt, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = phone_number,
                onValueChange = { phone_number = it },
                label = { Text("Phone Number") },
                placeholder = { Text("Enter your phone number") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            Spacer(modifier = Modifier.height(24.dp))

            VehicleColorDropdown()

            Spacer(modifier = Modifier.height(24.dp))

            TimePickerField()

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {
                    carViewModel.uploadCar(
                        imageUri = imageUri.value,
                        name = plate_number,
                        plate_number = plate_number,
                        vehicle_type = vehicle_type,
                        driver_name = driver_name,
                        phone_number = phone_number,
                        context = navController.context,
                        navController = navController,
                        color = color,
                        entry_time = entry_time,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Save Car",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddCarScreenPreview() {
    AddCarScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField() {
    val context = LocalContext.current

    var selectedTime by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        },
        hour,
        minute,
        true // true = 24-hour format
    )

    OutlinedTextField(
        value = selectedTime,
        onValueChange = {},
        label = { Text("Select Time") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                timePickerDialog.show()
            },
        shape = RoundedCornerShape(12.dp),
        leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleColorDropdown() {

    val colors = listOf(
        "Black", "White", "Silver", "Gray",
        "Blue", "Red", "Green", "Yellow",
        "Orange", "Brown", "Purple", "Pink", "Other"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selectedColor,
            onValueChange = {},
            readOnly = true,
            label = { Text("Vehicle Color") },
            placeholder = { Text("Select color") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.ColorLens, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            colors.forEach { color ->
                DropdownMenuItem(
                    text = { Text(color) },
                    onClick = {
                        selectedColor = color
                        expanded = false
                    }
                )
            }
        }
    }
}
