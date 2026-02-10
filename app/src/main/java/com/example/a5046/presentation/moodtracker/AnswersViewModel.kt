package com.example.a5046.presentation.moodtracker


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a5046.database.model.MoodTracker
import com.example.a5046.database.repository.MoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnswersViewModel (application: Application): AndroidViewModel(application) {

    private val moodRepository: MoodRepository
    //val allMoodTrack: LiveData<List<MoodTracker>> = moodRepository.allMoods.asLiveData()
    init{
        moodRepository = MoodRepository(application)
        moodRepository.getAllMoods()
        Log.i("init",  moodRepository.getAllMoods().value.toString())
    }
    val allMoodTrack: LiveData<List<MoodTracker>> = moodRepository.getAllMoods()

    fun insertMood(moodTracker: MoodTracker) = viewModelScope.launch(Dispatchers.IO) {
        moodRepository.insert(moodTracker)
    }
    fun updateMood(moodTracker: MoodTracker) = viewModelScope.launch(Dispatchers.IO) {
        moodRepository.update(moodTracker)
    }
    fun deleteMood(moodTracker: MoodTracker) = viewModelScope.launch(Dispatchers.IO) {
        moodRepository.delete(moodTracker)
    }

    fun getMood(currentDate: Long): MoodTracker {
        return moodRepository.getMood(currentDate)
    }

    fun accessAllMoods(): LiveData<List<MoodTracker>>{
        return  moodRepository.getAllMoods()
    }
}
