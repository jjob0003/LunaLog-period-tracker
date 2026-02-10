package com.example.a5046.database.repository

import android.app.Application
import com.example.a5046.database.UserDatabase
import com.example.a5046.database.dao.UserDataDao
import com.example.a5046.database.model.UserData
import kotlinx.coroutines.flow.Flow

class UserDataRepository (application: Application) {
    private var userDataDao: UserDataDao =
        UserDatabase.getDatabase(application).userDataDao()

    val userData: Flow<UserData?> = userDataDao.getUserData(1)

    suspend fun insert(userData: UserData) {
        userDataDao.insertUserData(userData)
    }
    suspend fun delete(userData: UserData) {
        userDataDao.deleteUserData(userData)
    }
    suspend fun update(userData: UserData) {
        userDataDao.updateUserData(userData)
    }

    suspend fun updateNameAndBirthday(name:String, birthday:Long) {
        userDataDao.updateNameAndBirthday(name, birthday)
    }


}


