package com.example.a5046.presentation.moodtracker

import android.icu.util.Calendar
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.a5046.database.model.MoodTracker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val softGreen = Color(163, 185, 166)
val forestGreen = Color(red = 124, green = 146, blue = 96)
val lightGreen = Color(red = 211, green = 239, blue = 198)


@RequiresApi(64)
@Composable
fun MoodScreen(modifier: Modifier = Modifier, answersViewModel: AnswersViewModel, datePicked: Long? = null) {

    var currentDate by remember { mutableStateOf(0L) }
    var feeling by remember { mutableStateOf("") }
    var periodFlow by remember { mutableStateOf("") }
    var painRating by remember { mutableStateOf("") }
    var painArea by remember { mutableStateOf("") }
    var energyLevel by remember { mutableStateOf("") }
    var loveLife by remember { mutableStateOf("") }
    var socialLife by remember { mutableStateOf("") }
    var sleep by remember { mutableStateOf("") }
    var cravings by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = softGreen)

    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (datePicked == null){
                    currentDate = DisplayDatePicker()
                }
                else{
                    currentDate = datePicked
                    DisplayDatePicker()
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Mood Tracker",
                    modifier = Modifier.padding(22.dp),
                    color = lightGreen,
                    fontSize = 24.sp
                )
            }
            Column(modifier = modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                Text(
                    text = " ${formatter.format(Date(currentDate))} ",
                    color = lightGreen,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(16.dp))
            Prompt("How are you feeling today?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                feeling = MoodBox("\uD83D\uDE0A", "Excited","\uD83E\uDD71", "Confident","\uD83D\uDE2C", "Anxious","\uD83E\uDD15","Sensitive", "🙏", "Grateful", "😌", "Calm")
            }

            Prompt("My period flow was...")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                periodFlow = MoodBox("🚫", "No flow","\uD83E\uDE78", "Light","\uD83E\uDE78", "Medium","🩸🩸","Heavy","\uD83E\uDE78\uD83E\uDE78","Very Heavy")
            }

            Prompt("My pain rating today...")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                painRating = MoodBox("\uD83D\uDE0A", "Pain free","\uD83D\uDE2C", "Mild","\uD83D\uDE15", "Moderate","\uD83D\uDE16","Extreme")
            }

            Prompt("In what area/s do you feel pain?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                painArea = MoodBox("\uD83D\uDC86\u200D♀", "Migraine","\uD83E\uDDB5", "Legs","\uD83E\uDDD8\u200D♀\uFE0F", "Back","\uD83D\uDCAA","Arms", "🦷","tooth", "💢", "cramps")
            }

            Prompt("How was your energy level today?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                energyLevel = MoodBox("\uD83D\uDECF\uFE0F", "Exhausted","\uD83E\uDDCD\u200D♀\uFE0F", "Low energy","\uD83D\uDEB6\u200D♀\uFE0F", "Energetic","\uD83E\uDD38\u200D♀\uFE0F","Super Energetic")
            }

            Prompt("How would you describe your love life?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                loveLife = MoodBox("\uD83D\uDEAB", "Not interested","\uD83E\uDD71", "OK","\uD83D\uDE18", "Great","\uD83D\uDE0D","Amazing")
            }

            Prompt("How would you describe your social life?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                socialLife = MoodBox("\uD83E\uDDCD\u200D♀\uFE0F", "Withdrawn","\uD83D\uDC6D", "Fairly Social","\uD83D\uDC6F\u200D♀\uFE0F", "Social","\uD83D\uDE2E\u200D\uD83D\uDCA8","Overtly social")
            }

            Prompt("How many hours of sleep did you get?")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                sleep = MoodBox("<3\uFE0F", "Below 3","3-6", "3 to 6","6-8", "6 to 8","8+","Over 8")
            }

            Prompt("Foods I am craving right now...")
            Box(
                modifier = modifier
                    .background(color = forestGreen)
                    .fillMaxWidth()
                    .padding(28.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                cravings = MoodBox("\uD83C\uDF55", "Pizza","\uD83C\uDF6B", "Chocolate","\uD83C\uDF5C", "Ramen","\uD83C\uDF54","Burger", "🍟","fries","🍰","cake")
            }

            val record = MoodTracker(currentDate, feeling, periodFlow, painRating, painArea, energyLevel, loveLife, socialLife, sleep, cravings)
            var displayDialog by remember{ mutableStateOf(false) }

            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Button(onClick = {
                    displayDialog = true
                    answersViewModel.insertMood(record) },
                    colors = ButtonDefaults.buttonColors(forestGreen))
                {
                    Text(text = "Save",
                        color = lightGreen)
                    if (displayDialog == true){
                        SaveDialog(onDismissRequest =  {
                            displayDialog = false
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SaveDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Your input has been recorded!",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                color = forestGreen,
                fontSize = 16.sp
            )
        }
    }
}

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDatePicker(): Long {

    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }
    Column(modifier = Modifier.padding(16.dp)) {
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            )
            {
                DatePicker(
                    state = datePickerState
                )
                selectedDate = datePickerState.selectedDateMillis!!
            }
        }
        Button(
            onClick = {
                showDatePicker = true
            },
            colors = ButtonDefaults.buttonColors(forestGreen),
            shape = CircleShape

        ) {
            Text(text = "Choose date",
                fontSize = 12.sp,
                color = lightGreen)
        }
    }
    return selectedDate
}

@Composable
fun Prompt(question: String){

    Row{
        Text(
            text = question,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            color = lightGreen,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoodBox(emoji1: String, answer1: String, emoji2: String, answer2: String, emoji3: String, answer3: String, emoji4: String, answer4: String, emoji5: String = "", answer5: String = "", emoji6: String = "", answer6: String = ""): String {

    var selectedAnswer by remember { mutableStateOf("") }
    var clickCounter by remember { mutableStateOf(0) }

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    clickCounter++
                    if (clickCounter == 1) {
                        selectedAnswer = answer1
                    } else if (clickCounter == 2) {
                        clickCounter = 0
                        selectedAnswer = ""
                    }
                }
                .border(
                    4.dp,
                    if (selectedAnswer == answer1)
                        Color.White
                    else
                        Color.Transparent
                )) {
            Box(
                modifier = Modifier
                    .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                CallEmoji(emoji1)
            }
            TextLabel(answer1)
        }
        Spacer(Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    clickCounter++
                    if (clickCounter == 1) {
                        selectedAnswer = answer2
                    } else if (clickCounter == 2) {
                        clickCounter = 0
                        selectedAnswer = ""
                    }
                }
                .border(
                    4.dp,
                    if (selectedAnswer == answer2)
                        Color.White
                    else
                        Color.Transparent
                )) {
            Box(
                modifier = Modifier
                    .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                CallEmoji(emoji2)
            }
            TextLabel(answer2)

        }
        Spacer(Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    clickCounter++
                    if (clickCounter == 1) {
                        selectedAnswer = answer3
                    } else if (clickCounter == 2) {
                        clickCounter = 0
                        selectedAnswer = ""
                    }
                }
                .border(
                    4.dp,
                    if (selectedAnswer == answer3)
                        Color.White
                    else
                        Color.Transparent
                )) {
            Box(
                modifier = Modifier
                    .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                CallEmoji(emoji3)
            }
            TextLabel(answer3)

        }
        Spacer(Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    clickCounter++
                    if (clickCounter == 1) {
                        selectedAnswer = answer4
                    } else if (clickCounter == 2) {
                        clickCounter = 0
                        selectedAnswer = ""
                    }
                }
                .border(
                    4.dp,
                    if (selectedAnswer == answer4)
                        Color.White
                    else
                        Color.Transparent
                )) {
            Box(
                modifier = Modifier
                    .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                CallEmoji(emoji4)
            }
            TextLabel(answer4)

        }
        Spacer(Modifier.width(16.dp))
        if (answer5 != ""){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        clickCounter++
                        if (clickCounter == 1) {
                            selectedAnswer = answer5
                        } else if (clickCounter == 2) {
                            clickCounter = 0
                            selectedAnswer = ""
                        }
                    }
                    .border(
                        4.dp,
                        if (selectedAnswer == answer5)
                            Color.White
                        else
                            Color.Transparent
                    )) {
                Box(
                    modifier = Modifier
                        .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    CallEmoji(emoji5)
                }
                TextLabel(answer5)
            }
        }
        Spacer(Modifier.width(16.dp))
        if (answer6 != ""){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        clickCounter++
                        if (clickCounter == 1) {
                            selectedAnswer = answer6
                        } else if (clickCounter == 2) {
                            clickCounter = 0
                            selectedAnswer = ""
                        }
                    }
                    .border(
                        4.dp,
                        if (selectedAnswer == answer6)
                            Color.White
                        else
                            Color.Transparent
                    )) {
                Box(
                    modifier = Modifier
                        .background(color = lightGreen, shape = RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    CallEmoji(emoji6)
                }
                TextLabel(answer6)
            }

        }
    }
    return selectedAnswer
}

@Composable
fun CallEmoji(moodEmoji: String){

    Text(
        text = moodEmoji,
        fontSize = 28.sp
    )
}

@Composable
fun TextLabel(label: String){

    Text(
        text = label,
        fontSize = 11.sp,
        modifier = Modifier,
        color = lightGreen
    )
}
