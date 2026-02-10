package com.example.a5046.database.dao

import androidx.room.*
import com.example.a5046.database.model.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {

    @Query("SELECT * FROM userData WHERE id = :id")
    fun getUserData(id: Int): Flow<UserData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserData)

    @Update
    suspend fun updateUserData(userData: UserData)

    @Query("UPDATE userData SET name =:name, birthDate = :birthday WHERE id = 1")
    suspend fun updateNameAndBirthday(name:String, birthday:Long)


    @Delete
    suspend fun deleteUserData(userData: UserData)


}