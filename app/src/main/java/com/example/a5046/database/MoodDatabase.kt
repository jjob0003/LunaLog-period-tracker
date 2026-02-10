package com.example.a5046.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a5046.database.dao.MoodTrackerDAO
import com.example.a5046.database.model.MoodTracker

@Database(entities = [MoodTracker::class], version = 1, exportSchema = false)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodTrackDAO(): MoodTrackerDAO
    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null
        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
