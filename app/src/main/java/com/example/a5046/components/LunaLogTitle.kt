package com.example.a5046.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.a5046.ui.theme.ForestGreen

@Composable
fun LunaLogTitle(title: String){
    Text(text = title + " \uD83C\uDF19",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        color = ForestGreen,
        fontSize = 20.sp, // Set the font size
        fontWeight = FontWeight.Bold )// Set the text bold)
}

@Preview
@Composable
fun LunaLogTitlePreview() {
    LunaLogTitle("Luna Log")
}