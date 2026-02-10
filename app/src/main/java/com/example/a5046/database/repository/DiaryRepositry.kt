package com.example.a5046.database.repositry
import com.example.a5046.database.model.Diary
import android.app.Application
import com.example.a5046.database.LunaLogDatabase
import com.example.a5046.database.dao.DiaryDao
import com.example.a5046.domain.WeatherObject
import com.example.a5046.domain.WeatherResponse
import kotlinx.coroutines.flow.Flow

class DiaryRepository (application: Application) {
    private var diaryDao: DiaryDao =
        LunaLogDatabase.getDatabase(application).diaryDAO()

    val allDiaries: Flow<List<Diary>> = diaryDao.getAllDiaries()

    fun getAllEntries(): Flow<List<Diary>> {
        return diaryDao.getAllDiaries()
    }
    suspend fun getEntry(id: Int): Diary{
        return diaryDao.getDiary(id)
    }
    suspend fun insert(diary: Diary) {
        diaryDao.insertDiary(diary)
    }
    suspend fun delete(diary: Diary) {
        diaryDao.deleteDiary(diary)
    }
    suspend fun update(diary: Diary) {
        diaryDao.updateDiary(diary)
    }

    private val weatherService = WeatherObject.weatherService
    suspend fun getWeather(): WeatherResponse {
        return weatherService.getWeather()
    }
}
