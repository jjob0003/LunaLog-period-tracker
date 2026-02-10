package com.example.a5046.database.dao

import androidx.room.*
import com.example.a5046.database.model.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary")
    fun getAllDiaries(): Flow<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    suspend fun getDiary(id: Int): Diary

    @Query("SELECT * FROM diary WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun getDiaryByTitle(query: String): List<Diary>

    @Insert
    suspend fun insertDiary(diary: Diary)

    @Insert
    suspend fun insertDiaries(diary: List<Diary>)

    @Update
    suspend fun updateDiary(diary: Diary)

    @Delete
    suspend fun deleteDiary(diary: Diary)

}
