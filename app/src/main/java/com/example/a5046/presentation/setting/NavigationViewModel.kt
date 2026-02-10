package com.example.a5046.presentation.setting
import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a5046.database.model.UserData
import com.example.a5046.database.repository.UserDataRepository
import com.example.a5046.database.repositry.PeriodRepositry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NavigationViewModel(application: Application) : AndroidViewModel(application) {

    private val userDataRepository: UserDataRepository
    private val periodRepositry: PeriodRepositry

    init {
        userDataRepository = UserDataRepository(application)
        periodRepositry = PeriodRepositry(application)
    }

    val userData: LiveData<UserData?> = userDataRepository.userData.asLiveData()
    val name: MutableState<String> = mutableStateOf("")
    val birthDate: MutableState<Long?> = mutableStateOf(null)
    val lastDate: MutableState<Long?> = mutableStateOf(null)
    val periodLength: MutableState<String> = mutableStateOf("")
    val cycleLength: MutableState<String> = mutableStateOf("")

    fun setName(newName: String) {
        this.name.value = newName
    }

    fun setBirthDate(dateMillis: Long) {
        this.birthDate.value = dateMillis
    }

    fun setLastDate(dateMillis: Long) {
        this.lastDate.value = dateMillis
    }


    fun setPeriodLength(newPeriod: String) {
        this.periodLength.value = newPeriod
    }

    fun setCycleLength(newCycle: String) {
        this.cycleLength.value = newCycle
    }

    fun insertUserData(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
        userDataRepository.insert(userData)
    }

//    fun updateUserData(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
//        userDataRepository.update(userData)
//    }

//    fun deleteUserData(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
//        userDataRepository.delete(userData)
//    }

    fun updateUserInfo(newUserName: String, newBirthDate: Long?) {
        viewModelScope.launch(Dispatchers.IO){
            userDataRepository.updateNameAndBirthday(newUserName, newBirthDate!!)
        }
    }

//    fun updateUserInfo(newUserName: String, newBirthDate: Long?) {
//        val currentUserData = userData.value
//        viewModelScope.launch(Dispatchers.IO) {
//            if (currentUserData != null) {
//                val updatedUserData = UserData(
//                    id = 1,
//                    name = newUserName,
//                    birthDate = newBirthDate,
//                    lastDate = currentUserData.lastDate,
//                    periodLength = currentUserData.periodLength,
//                    cycleLength = currentUserData.cycleLength
//                )
//                userDataRepository.update(updatedUserData)
//            }
//        }
//
//    }
}

