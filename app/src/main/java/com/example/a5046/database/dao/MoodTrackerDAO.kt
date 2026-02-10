package com.example.a5046.database.dao

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import androidx.room.*
import com.example.a5046.database.model.MoodTracker

@Dao
interface MoodTrackerDAO {
    @Query("SELECT * FROM moodTracker")
    fun getAllMood(): LiveData<List<MoodTracker>>
    @Query("SELECT * FROM moodTracker WHERE currentDate = :currentDate")
    fun getMood(currentDate: Long): MoodTracker
    @Insert
    suspend fun insertMood(moodTracker: MoodTracker)
    @Update
    suspend fun updateMood(moodTracker: MoodTracker)
    @Delete
    suspend fun deleteMood(moodTracker: MoodTracker)
}