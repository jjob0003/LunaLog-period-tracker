package com.example.a5046.presentation.userdata

import com.example.a5046.presentation.setting.NavigationViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.a5046.presentation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserName(
    navController: NavHostController,
    navViewModel: NavigationViewModel
) {
    val name = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
//                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
//                        color = ForestGreen,
//                        fontSize = 20.sp, // Set the font size
//                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(200.dp))

            Text(
                text = "Please enter your user name",
                color = ForestGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    if (it.length <= 10 && !it.contains(" ")) {
                        name.value = it
                        errorMessage.value = ""
                        navViewModel.setName(name.value)
                    } else {
                        coroutineScope.launch {
                            delay(500)
                            errorMessage.value = "Username should be less than 10 characters and contain no spaces."
                            delay(2500)
                            errorMessage.value = ""
                        }
                    }
                },
                label = {
                    Text(
                        "User Name",
                        style = TextStyle(
                            color = ForestGreen,
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = SoftGreen,
                    unfocusedBorderColor = ForestGreen
                ),
                modifier = Modifier.padding(start = 10.dp)
            )


            if (errorMessage.value.isNotEmpty()) {
                Text(
                    text = errorMessage.value,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }


            Spacer(modifier = Modifier.size(120.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(60.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = {navController.navigate(Routes.BirthDate.value)}, colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)) {
                    Text("Skip", style =
                    MaterialTheme.typography.bodyLarge)
                }


//                onClick = {
//                    val userData = UserData(
//                        1,
//                        name = navViewModel.name.value,
//                        birthDate = navViewModel.birthDate.value,
//                        lastDate = navViewModel.lastDate.value,
//                        periodLength = navViewModel.periodLength.value,
//                        cycleLength = navViewModel.cycleLength.value
//                    )
//                    navViewModel.insertUserData(userData)
//                    navController.navigate(Routes.Main.value) },
//                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                FilledTonalButton(
                    onClick = {
                        navController.navigate(Routes.BirthDate.value)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
                    Text("Next", style =
                    MaterialTheme.typography.bodyLarge)
                }

            }

        }
    }
}