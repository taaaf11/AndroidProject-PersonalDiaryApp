package com.example.personaldiaryapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryVM( application: Application ): AndroidViewModel(application)
{
    val readAllDiaryEntrys: LiveData<List<DiaryEntry>>
//     val readAllSettings: List<AppSettings>
    val repository: DiaryRepository
    val getUsername: LiveData<String>
    val getPassword: LiveData<String>
//    val getLoggedInStatus: LiveData<Boolean>
//    val getThemeMode: LiveData<Boolean>

    init {
        val appSettingsDao = AppDataBase.getDataBase(application).appSettingsDao()
        val diaryEntrysDao = AppDataBase.getDataBase(application).diaryEntryDao()
        repository = DiaryRepository(appSettingsDao, diaryEntrysDao)
        readAllDiaryEntrys = repository.readAllDiaryEntries
        getUsername = repository.getUsername
        getPassword = repository.getPassword
//        getLoggedInStatus = repository.getLoggedInStatus
//        getThemeMode =  repository.getThemeMode
    }

    fun addDiaryEntry(entry: DiaryEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDiaryEntry(entry)
        }
    }

    fun deleteDiaryEntry(entry: DiaryEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDiaryEntry(entry)
        }
    }

    fun updateDiaryEntry(entry: DiaryEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDiaryEntry(entry)
        }
    }

    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setUsername(username)
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setPassword(password)
        }
    }

//    fun setLoggedInStatus(isLoggedIn: Boolean) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.setLoggedInStatus(isLoggedIn)
//        }
//    }

    fun setCredentials(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setCredientials(username, password)
        }
    }

//    fun setLightMode() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.setLightMode()
//        }
//    }

//    fun setDarkMode() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.setDarkMode()
//        }
//    }

    fun setDefaultSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDefaultSettings()
        }
    }
//    fun verifyCredentials(username: String, password: String): Boolean {
//        var rv = false
//        viewModelScope.launch(Dispatchers.IO) {
//            rv = repository.verifyCredentials(username, password)
//        }
//        return rv
//    }

//    fun addUser(appSettings: AppSettings) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addUser(appSettings)
//        }
//    }
}