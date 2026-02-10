package com.example.a5046.presentation.signup

import SignUpViewModel
import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.util.ValidationUtils.isvalidatePassword


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {

    val emailAddress = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val checkedSignUp = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val viewModel: SignUpViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    val isFormValid = remember(emailAddress.value, password.value, checkedSignUp.value) {
        emailAddress.value.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(emailAddress.value).matches() &&
                isvalidatePassword(password.value) && checkedSignUp.value
    }

    fun validateEmailAddress(newValue: String) {
        emailAddress.value = newValue
        emailError.value = if (!Patterns.EMAIL_ADDRESS.matcher(newValue).matches() && newValue.isNotBlank()) {
            "Invalid email format"
        } else {
            ""
        }
    }

    fun validatePassword(newValue: String) {
        password.value = newValue
        passwordError.value = if (!isvalidatePassword(newValue) && newValue.isNotBlank()) {
            "Password should contain an uppercase and \n must be at least 6 letters."
        } else {
            ""
        }
    }

    // Collecting sign-up status updates from ViewModel
    val signUpResult = viewModel.signUpResult.collectAsState(initial = Result.success("")).value

    // Handle sign-up result
    // React to changes in signUpResult using snackbar
    LaunchedEffect(signUpResult) {
        signUpResult.onSuccess {resultMessage ->
            // Check if resultMessage is not empty before showing the snackbar
            if (resultMessage.isNotBlank()) {
                snackbarHostState.showSnackbar("Registration successful: $resultMessage")
                // Navigate only when email and password are not empty
                if (emailAddress.value.isNotBlank() && password.value.isNotBlank()) {
                    navController.navigate(Routes.Username.value)
                }
            }
        }.onFailure {
            snackbarHostState.showSnackbar("Registration failed: ${it.message}")
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
            item {
                Text(
                    text = "Ready to join the fun? \nLet's get started! ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestGreen
                )
            }


            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    OutlinedTextField(
                        value = emailAddress.value,
                        onValueChange = {
                            validateEmailAddress(it)
                        },
                        label = { Text("Email Address *") },
                        isError = emailError.value.isNotEmpty(),
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Address") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (emailError.value.isNotEmpty()) Color.Red else SoftGreen,
                            unfocusedBorderColor = if (emailError.value.isNotEmpty()) Color.Red else ForestGreen
                        )
                    )}
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (emailError.value.isNotEmpty()) {
                        Text(emailError.value, color = Color.Red, fontSize = 12.sp)
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            validatePassword(it)
                        },
                        label = { Text("Password *") },
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
                    )}
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (passwordError.value.isNotEmpty()) {
                        Text(passwordError.value, color = Color.Red, fontSize = 12.sp)
                    }

                }
            }
            item {
                Spacer(modifier = Modifier.size(10.dp))
            }


            item {
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkedSignUp.value,
                        onCheckedChange = { isChecked -> checkedSignUp.value = isChecked }
                    )
                    Text(
                        "I agree with the terms and conditions.", style =
                        MaterialTheme.typography.bodyLarge, fontSize = 12.sp
                    )
                }

            }
            item {
                FilledTonalButton(
                    onClick = {
                        if (isFormValid) {
                            viewModel.signUpUser(emailAddress.value, password.value)
                        }
                    },
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(containerColor = if (isFormValid) SoftGreen else Color.Gray)
                ) {
                    Text("Sign up", style = MaterialTheme.typography.bodyLarge)
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have an account?   ", style = MaterialTheme.typography.bodyMedium)
                    FilledTonalButton(
                        onClick = {
                            // Navigate to the sign-in screen
                            navController.navigate(Routes.SignIn.value)
                        }, colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)

                    ) {
                        Text("Sign in", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }




        }
    }
}

