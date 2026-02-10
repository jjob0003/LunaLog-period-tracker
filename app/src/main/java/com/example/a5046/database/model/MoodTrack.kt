package com.example.a5046.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moodTracker")
data class MoodTracker(
    val currentDate: Long = 0L,
    val feeling: String = "",
    val periodFlow: String = "",
    val painRating: String = "",
    val painArea: String = "",
    val energyLevel: String = "",
    val loveLife: String = "",
    val socialLife: String = "",
    val sleep: String = "",
    val cravings: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
