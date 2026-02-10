package com.example.a5046.presentation.report

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.components.CardWithTitle
import com.example.a5046.database.model.MoodTracker
import com.example.a5046.presentation.moodtracker.AnswersViewModel
import com.example.a5046.presentation.moodtracker.forestGreen
import com.example.a5046.presentation.period.PeriodViewModel
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen
import java.util.Calendar
import java.util.Date



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavHostController, answersViewModel: AnswersViewModel) {
    val periodViewModel: PeriodViewModel = viewModel()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Dashboard  \uD83C\uDF19", modifier =
        Modifier.padding(start = 16.dp),
            color = ForestGreen,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ) }) }
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),

                ) {
                item {
                    BarChartScreen()
                }

                item {
                    CardWithTitle("Mood of the Month") {

                        val calendar = Calendar.getInstance()
                        val thisMonth = calendar.get(Calendar.MONTH) + 1
                        val thisYear = calendar.get(Calendar.YEAR)


                        val moodData = answersViewModel.accessAllMoods().observeAsState(initial = emptyList())
                        val allMoodList: List<MoodTracker>? = moodData.value

                        var feelingsByMonth = mutableListOf<List<String>>()


                        if(allMoodList != null){
                            for(each in allMoodList){
                                val recordedDate: Date = Date(each.currentDate)

                                val calendarMood = Calendar.getInstance().apply {time = recordedDate}
                                val thisMoodMonth = calendarMood.get(Calendar.MONTH) + 1
                                val thisMoodYear = calendarMood.get(Calendar.YEAR)

                                if(thisMoodMonth == thisMonth && thisMoodYear == thisYear){
                                    val feel = each.feeling.split(",")
                                    feelingsByMonth.add(feel)
                                }
                            }

                            val emptyListsRemoved = feelingsByMonth.filter { it != listOf("") }.toMutableList()
                            feelingsByMonth = emptyListsRemoved

                            val moodTypes = feelingsByMonth.distinct()
                            var mostFrequentMood: List<String> = listOf()
                            var frequencyCount = 0
                            var countHolder = 0

                            for(moodList in moodTypes){

                                for(each in feelingsByMonth){
                                    if(each == moodList) {
                                        frequencyCount++
                                    }
                                }
                                if(frequencyCount > countHolder){
                                    mostFrequentMood = moodList
                                    countHolder = frequencyCount
                                }
                                frequencyCount = 0

                            }

                            if("Excited" in mostFrequentMood){
                                Text(text = "\uD83D\uDE0A",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                        textAlign = TextAlign.Center)
                                Text(text = "Excited",
                                    modifier = Modifier
                                        .padding(24.dp)
                                    .fillMaxWidth(),
                                fontSize = 50.sp,
                                    color = forestGreen,
                                textAlign = TextAlign.Center)
                            }
                            else if("Confident" in mostFrequentMood){
                                Text(text = "\uD83E\uDD71",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Confident",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Anxious" in mostFrequentMood){
                                Text(text = "\uD83D\uDE2C",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Anxious",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Sensitive" in mostFrequentMood){
                                Text(text = "\uD83E\uDD15",
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                                fontSize = 50.sp,
                                textAlign = TextAlign.Center)
                                Text(text = "Sensitive",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Grateful" in mostFrequentMood){
                                Text(text = "\uD83D\uDE4F",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Grateful",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Calm" in mostFrequentMood){
                                Text(text = "\uD83D\uDE0C",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Calm",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }

                        }else{
                            NoDataToDisplayText()
                        }
                    }
                    }

                item {
                    CardWithTitle("Flow Tracker") {
                        val calendar = Calendar.getInstance()
                        val thisMonth = calendar.get(Calendar.MONTH) + 1
                        val thisYear = calendar.get(Calendar.YEAR)


                        val moodData = answersViewModel.accessAllMoods().observeAsState(initial = emptyList())
                        val allMoodList: List<MoodTracker>? = moodData.value

                        var flowByMonth = mutableListOf<List<String>>()


                        if(allMoodList != null){
                            for(each in allMoodList){
                                val recordedDate: Date = Date(each.currentDate)

                                val calendarMood = Calendar.getInstance().apply {time = recordedDate}
                                val thisMoodMonth = calendarMood.get(Calendar.MONTH) + 1
                                val thisMoodYear = calendarMood.get(Calendar.YEAR)

                                if(thisMoodMonth == thisMonth && thisMoodYear == thisYear){
                                    val flow = each.periodFlow.split(",")
                                    flowByMonth.add(flow)
                                }
                            }

                            val emptyListsRemoved = flowByMonth.filter { it != listOf("") }.toMutableList()
                            flowByMonth = emptyListsRemoved

                            val moodTypes = flowByMonth.distinct()
                            var mostFrequentMood: List<String> = listOf()
                            var frequencyCount = 0
                            var countHolder = 0

                            for(moodList in moodTypes){

                                for(each in flowByMonth){
                                    if(each == moodList) {
                                        frequencyCount++
                                    }
                                }
                                if(frequencyCount > countHolder){
                                    mostFrequentMood = moodList
                                    countHolder = frequencyCount
                                }
                                frequencyCount = 0

                            }

                            if("No flow" in mostFrequentMood){
                                Text(text = "\uD83D\uDEAB",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "No flow",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Light" in mostFrequentMood){
                                Text(text = "\uD83E\uDE78",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Light",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Medium" in mostFrequentMood){
                                Text(text = "\uD83E\uDE78",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Medium",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Heavy" in mostFrequentMood){
                                Text(text = "\uD83E\uDE78\uD83E\uDE78",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Heavy",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Very Heavy" in mostFrequentMood){
                                Text(text = "\uD83E\uDE78\uD83E\uDE78",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Very Heavy",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }

                        }else{
                            NoDataToDisplayText()
                        }
                    }
                }

                item {
                    CardWithTitle("Energy Level for this Month") {

                        val calendar = Calendar.getInstance()
                        val thisMonth = calendar.get(Calendar.MONTH) + 1
                        val thisYear = calendar.get(Calendar.YEAR)


                        val moodData = answersViewModel.accessAllMoods().observeAsState(initial = emptyList())

                        val allMoodList: List<MoodTracker>? = moodData.value

                        var energyByMonth = mutableListOf<List<String>>()


                        if(allMoodList != null){
                            for(each in allMoodList){
                                val recordedDate: Date = Date(each.currentDate)

                                val calendarMood = Calendar.getInstance().apply {time = recordedDate}
                                val thisMoodMonth = calendarMood.get(Calendar.MONTH) + 1
                                val thisMoodYear = calendarMood.get(Calendar.YEAR)

                                if(thisMoodMonth == thisMonth && thisMoodYear == thisYear){
                                    val energy = each.energyLevel.split(",")
                                    energyByMonth.add(energy)
                                }
                            }

                            val emptyListsRemoved = energyByMonth.filter { it != listOf("") }.toMutableList()
                            energyByMonth = emptyListsRemoved

                            val energyLevels = energyByMonth.distinct()
                            var mostFrequentEnergy: List<String> = listOf()
                            var frequencyCount = 0
                            var countHolder = 0

                            for(eachEnergy in energyLevels){

                                for(each in energyByMonth){
                                    if(each == eachEnergy) {
                                        frequencyCount++
                                    }
                                }
                                if(frequencyCount > countHolder){
                                    mostFrequentEnergy = eachEnergy
                                    countHolder = frequencyCount
                                }
                                frequencyCount = 0
                            }

                            if("Exhausted" in mostFrequentEnergy){
                                Text(text = "\uD83D\uDECF\uFE0F",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Exhausted",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Low energy" in mostFrequentEnergy){
                                Text(text = "\uD83E\uDDCD\u200D♀\uFE0F",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Low energy",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Energetic" in mostFrequentEnergy){
                                Text(text = "\uD83D\uDEB6\u200D♀\uFE0F",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Energetic",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }
                            else if("Super Energetic" in mostFrequentEnergy){
                                Text(text = "\uD83E\uDD38\u200D♀\uFE0F",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center)
                                Text(text = "Super Energetic",
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    fontSize = 50.sp,
                                    color = forestGreen,
                                    textAlign = TextAlign.Center)
                            }

                        }else{
                            NoDataToDisplayText()
                        }
                    }
                    }
            }}
    }
}

@Composable
fun NoDataToDisplayText() {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "No Data to display",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp
        )
        Button(
            onClick = {  },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)
        ) {
            Text(text = "Log",
                fontWeight = FontWeight.Bold)
        }
    }
}
