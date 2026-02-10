package com.example.a5046.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.a5046.database.MoodDatabase
import com.example.a5046.database.dao.MoodTrackerDAO
import com.example.a5046.database.model.MoodTracker
import kotlinx.coroutines.flow.Flow
class MoodRepository(application: Application) {
    private var moodTrackerDAO: MoodTrackerDAO =
        MoodDatabase.getDatabase(application).moodTrackDAO()

//    val allMoods: LiveData<List<MoodTracker>> = moodTrackerDAO.getAllMood()

    fun getAllMoods(): LiveData<List<MoodTracker>>{
        return moodTrackerDAO.getAllMood()
    }

    suspend fun insert(moodTracker: MoodTracker) {
        moodTrackerDAO.insertMood(moodTracker)
    }

    fun getMood(date: Long): MoodTracker {
        return moodTrackerDAO.getMood(date)
    }

    suspend fun update(moodTracker: MoodTracker) {
        moodTrackerDAO.updateMood(moodTracker)
    }

    suspend fun delete(moodTracker: MoodTracker) {
        moodTrackerDAO.deleteMood(moodTracker)
    }
}
