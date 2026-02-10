package com.example.a5046.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a5046.database.dao.DiaryDao
import com.example.a5046.database.dao.PeriodDao
import com.example.a5046.database.model.Diary
import com.example.a5046.database.model.Period

@Database(entities = [Diary::class, Period::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class LunaLogDatabase : RoomDatabase() {

    abstract fun diaryDAO(): DiaryDao
    abstract fun periodDAO(): PeriodDao

    companion object {
        @Volatile
        private var INSTANCE: LunaLogDatabase? = null
        fun getDatabase(context: Context): LunaLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LunaLogDatabase::class.java,
                    "luna_log_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
