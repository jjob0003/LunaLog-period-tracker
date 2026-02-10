package com.example.a5046.presentation.setting

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a5046.ui.theme.ForestGreen
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.util.ValidationUtils.isvalidatePassword
import com.example.a5046.ui.theme.SoftGreen


import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordPage(navController: NavController){

    val password = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf("") }
    val isSaveEnabled = passwordError.value.isEmpty() && newPassword.value == password.value
            && password.value.isNotEmpty() && newPassword.value.isNotEmpty()
    //  val newPasswordError = remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    fun validatePassword(newValue: String) {
        password.value = newValue
        passwordError.value = if (!isvalidatePassword(newValue) && newValue.isNotBlank()) {
            "Password should contain an uppercase and \n must be at least 6 letters."
        } else {
            ""
        }
    }

    fun validateNewPassword(newValue: String) {
        newPassword.value = newValue
        passwordError.value = when {
            newValue.isBlank() -> "New password cannot be empty"
            !isvalidatePassword(newValue) -> "Password should contain an uppercase letter and \n" +
                    " must be at least 6 characters long."
            newValue != password.value -> "Passwords do not match"
            else -> ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
            )
        }
    ){paddingValues ->

        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Spacer(modifier = Modifier.size(40.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "New Password  ", color = ForestGreen,
                        fontSize = 13.sp, // Set the font size
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                            validatePassword(it)
                        },
                        label = { Text("Password", color = ForestGreen) },
                        isError = passwordError.value.isNotEmpty(),
                        leadingIcon = {
                            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                                Icon(
                                    imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (passwordError.value.isNotEmpty()) Color.Red else SoftGreen,
                            unfocusedBorderColor = if (passwordError.value.isNotEmpty()) Color.Red else ForestGreen
                        )
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "New Password  ", color = ForestGreen,
                        fontSize = 13.sp, // Set the font size
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = newPassword.value,
                        onValueChange = {
                            newPassword.value = it
                            validateNewPassword(it)
                        },
                        label = { Text("Password", color = ForestGreen) },
                        isError = passwordError.value.isNotEmpty(),
                        leadingIcon = {
                            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                                Icon(
                                    imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (passwordError.value.isNotEmpty()) Color.Red else SoftGreen,
                            unfocusedBorderColor = if (passwordError.value.isNotEmpty()) Color.Red else ForestGreen
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (passwordError.value.isNotEmpty()) {
                        Text(passwordError.value, color = Color.Red, fontSize = 12.sp)
                    }

                }
            }
            item {
                Spacer(modifier = Modifier.size(50.dp))
            }
            item{
                FilledTonalButton(
                    onClick = { updatePassword(password.value)
                        showDialog = true},
                    enabled = isSaveEnabled,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
                    Text("Save")
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        text = { Text("Password has been updated successfully.",
                            style = TextStyle(
                            color = ForestGreen,
                        )) },
                        confirmButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }

            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
        }

    }
}

fun updatePassword(password: String) {
    // [START update_password]
    val user = Firebase.auth.currentUser
    val newPassword = password

    user!!.updatePassword(newPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(ContentValues.TAG, "User password updated.")
            }
        }
    // [END update_password]

}

