package com.example.a5046.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5046.ui.theme.SoftGreen

@Composable
fun CardWithTitle(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            ) {
            Text(
                title,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = SoftGreen, // Set the text color
                fontWeight = FontWeight.Bold // Set the text bold
            )
            content()
        }
    }
}
