package com.example.a5046.presentation.diary

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
//import com.example.a5046.domain.WeatherResponse
import com.example.a5046.database.model.Diary
import com.example.a5046.database.repositry.DiaryRepository
import com.example.a5046.domain.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application) : AndroidViewModel(application)  {
    var uiState by mutableStateOf(UiState())
        private set


    private var cRepository: DiaryRepository
    private var getEntriesJob: Job? = null

    init{
        cRepository = DiaryRepository(application)
        getEntries()
    }

    private fun getEntries() {
        getEntriesJob?.cancel()
        getEntriesJob = cRepository.getAllEntries()
            .onEach { entries ->
                uiState = uiState.copy(
                    entries = entries,
                )
            }.launchIn(viewModelScope)

    }

    val allDiaries: LiveData<List<Diary>> = cRepository.allDiaries.asLiveData()

    fun getEntry(entryId: Int) {
        viewModelScope.launch {
            val entry = cRepository.getEntry(entryId)
            uiState = uiState.copy(
                entry = entry
            )
            Log.i("single entry", uiState.entry.toString())
        }
    }
    fun addDiary(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(diary)
    }
    fun updateDiary(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(diary)
    }
    fun deleteDiary(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(diary)
    }

    data class Weather(
        val longitude : Int,
        val latitude: Int
    )
    data class UiState(
        val entries: List<Diary> = emptyList(),
//        val entriesOrder: Order = Order.DateModified(OrderType.ASC()),
        val entry: Diary? = null,
        val error: String? = null,
//        val searchEntries: List<Diary> = emptyList(),
        val navigateUp: Boolean = false,
        val chartEntries : List<Diary> = emptyList(),
        val readingMode: Boolean = true
    )

    var weather:MutableState<WeatherResponse> = mutableStateOf(WeatherResponse())

    fun getResponse(){
        viewModelScope.launch {
            try {
                val responseReturned = cRepository.getWeather()
                weather.value = responseReturned
            } catch (e: Exception) {
                Log.i("Error ", e.toString())
            }
        }
    }
}
