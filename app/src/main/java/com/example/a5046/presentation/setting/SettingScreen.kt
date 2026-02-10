package com.example.a5046.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.moodtracker.forestGreen
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("Setting  \uD83C\uDF19", modifier =
        Modifier.padding(start = 16.dp), // Add padding to the title
            color = ForestGreen, // Set the text color
            fontSize = 20.sp, // Set the font size
            fontWeight = FontWeight.Bold
        ) }) }
    ){paddingValues ->
        Column() {
            Text(
                text = "Setting",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 10.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGreen
                ),
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = "Check Your Profile",
                            color = ForestGreen,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Button(
                            modifier = Modifier.padding(top = 10.dp),
                            onClick = { navController.navigate(Routes.Profile.value) },
                            colors = ButtonDefaults.buttonColors(SoftGreen),
                            contentPadding = PaddingValues(horizontal = 30.dp),
                        ) {
                            Text(
                                text = "View",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = "",
                        modifier = Modifier.height(120.dp)

                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "General",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    color = ForestGreen
                )
                Spacer(modifier = Modifier.width(10.dp))
                GeneralSettingItem(
                    icon = R.drawable.password,
                    mainText = "Password",
                    subText = "Change password",
                    onRowClick = { navController.navigate(Routes.Password.value) },
                )
                GeneralSettingItem(
                    icon = R.drawable.notification,
                    mainText = "Notifications",
                    subText = "Customize notifications",
                    onRowClick = { navController.navigate(Routes.Notification.value) },
                    )
            }
            Spacer(modifier = Modifier.width(50.dp))
            LazyColumn(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally){
                item{
                    FilledTonalButton(
                        onClick = {
                            Firebase.auth.signOut()
                            navController.navigate(Routes.SignIn.value) },
                        colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)) {
                        Text("Log Out") }}
                }
            }
    }

}





@Composable
fun GeneralSettingItem(
    icon: Int,
    mainText: String,
    subText: String,
    onRowClick: () -> Unit,
    //  backgroundColor: Color = Color.White

) {
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        ),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp).clickable (onClick = onRowClick ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp),
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp),
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )

                    Text(
                        text = subText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp),
                        color = SoftGreen
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
