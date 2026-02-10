//package com.example.a5046.controller.util
//
//import android.annotation.SuppressLint
//import android.content.pm.PackageManager
//import android.location.LocationManager
//import androidx.activity.ComponentActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//
//class GetLocation: ComponentActivity() {
//    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
//
//    fun create() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST_CODE
//            )
//        } else {
//            getLocation()
//        }
//    }
//
//     cloverride fun onRequestPermissionsResult(
//         requestCode: Int,
//         permissions: Array<out String>,
//         grantResults: IntArray
//     ) {
//        super.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLocation()
//            } else {
//                // Handle permission denied
//            }
//        }
//    }
//
//    private fun getLocation() {
//        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
//        if (locationManager != null) {
//            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            val latitude = location?.latitude
//            val longitude = location?.longitude
//        }
//    }
//
//}