package com.example.a5046.presentation.signin

import SignUpViewModel
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.presentation.Routes
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import androidx.compose.ui.text.AnnotatedString
import kotlin.Result

@Composable
fun LoginFeedback(result: Result<String>?,
                  navController: NavHostController,
                  errorMessage: MutableState<String>
) {
    result?.onSuccess {
        LaunchedEffect(navController) {
            navController.navigate(Routes.Main.value)
        }
    }?.onFailure { exception ->
//
        errorMessage.value = "Incorrect login details. Please try again."

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController) {
    val emailAddress = remember { mutableStateOf("") }
    val password = remember {
        mutableStateOf("")
    }
    val viewModel: SignUpViewModel = viewModel()
    val passwordVisible = remember { mutableStateOf(false) }
    val signInResult = viewModel.signInResult.collectAsState(initial = null)
    val context = LocalContext.current
    val errorMessage = remember { mutableStateOf("") }

    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val googleSignInOptions = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, googleSignInOptions) }
    val googleSignInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogle(credential)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign-in failed", e)
        }
    }

    Scaffold(

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Luna Log!\uD83C\uDF19",
                    style = MaterialTheme.typography.headlineSmall,
                    color = ForestGreen
                )
                Spacer(modifier = Modifier.size(40.dp))
                Text(text = "Welcome!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = LightGreen
                )
                Text(text = "Start your journey",
                    style = MaterialTheme.typography.headlineSmall,
                    color = SoftGreen
                )
                Text(text = "To Understand your body and mind",
                    style = MaterialTheme.typography.headlineSmall,
                    color = ForestGreen
                )


                Row{Spacer(modifier = Modifier.size(40.dp))
                    if (errorMessage.value.isNotEmpty()) {
                        Text(errorMessage.value, color = Color.Red)
                    }
                    LoginFeedback(signInResult.value, navController, errorMessage)
                    Spacer(modifier = Modifier.size(40.dp))
                }

                OutlinedTextField(
                    value = emailAddress.value,
                    onValueChange = { emailAddress.value = it
                    },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Address") },
                    label = { Text("Email Address",
                        style = TextStyle(
                            color = ForestGreen,
                        )
                    ) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = SoftGreen,
                        unfocusedBorderColor = ForestGreen
                    )
                )

                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it
                    },
                    label = { Text("Password",
                        style = TextStyle(
                            color = ForestGreen,
                        )
                    ) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = SoftGreen,
                        unfocusedBorderColor = ForestGreen
                    ),
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {
                        val image = if (passwordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Localized description for accessibility services
                        val description = if (passwordVisible.value) "Hide password" else "Show password"

                        // Toggle button to hide or display password
                        IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )
                Row {
                    FilledTonalButton(
                        onClick = {navController.navigate(Routes.SignUp.value)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                    ) {
                        Text("Sign Up", style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.size(20.dp))

                    Spacer(modifier = Modifier.size(20.dp))
                    FilledTonalButton(onClick = { viewModel.signInUser(emailAddress.value, password.value)

                    }, colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
                        Text("Log In", style =
                        MaterialTheme.typography.bodyLarge)
                    }

                }

                Row{

                    FilledTonalButton(onClick = {googleSignInLauncher.launch(googleSignInClient.signInIntent)}) {
                        Text("Sign In with Google")
                        Image(
                            painter = painterResource(id = R.drawable.google_login),
                            contentDescription = "Sign in with Google",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Row{
                    ClickableText(text = AnnotatedString("Forget password?"), onClick = {


                    })

                }

            }
        }
    }
}
