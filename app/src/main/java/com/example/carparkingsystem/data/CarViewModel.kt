package com.example.carparkingsystem.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carparkingsystem.navigation.ROUTE_DASHBOARD


import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class CarViewModel:ViewModel() {
    val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dzbc0s47e/image/upload"
    val uploadPreset = "pic_folder"

    fun uploadCar(imageUri: Uri?,name:String,plate_number:String,vehicle_type:String,driver_name:String,phone_number:String,
                      context: Context,navController: NavController){

        viewModelScope.launch (Dispatchers.IO){
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context,it) }
                val ref = FirebaseDatabase.getInstance().getReference("Cars").push()
                val carData = mapOf(
                    "id" to ref.key,
                    "plate_number" to plate_number,
                    "vehicle_type" to vehicle_type,
                    "driver_name" to driver_name,
                    "phone_number" to phone_number,
                    "imageUrl" to imageUrl
                )
                ref.setValue(carData).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Car saved Successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Car not saved",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadToCloudinary(context:Context,uri: Uri):String{
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw  Exception("Image read failed")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file","image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(),fileBytes))
            .addFormDataPart("upload_preset",uploadPreset).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if(!response.isSuccessful) throw Exception("Upload failed")
        val responseBody = response.body?.string()
        val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image URL")
    }







}