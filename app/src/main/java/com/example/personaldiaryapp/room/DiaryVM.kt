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

    init {
        val appSettingsDao = AppDataBase.getDataBase(application).appSettingsDao()
        val diaryEntrysDao = AppDataBase.getDataBase(application).diaryEntryDao()
        repository = DiaryRepository(appSettingsDao, diaryEntrysDao)
        readAllDiaryEntrys = repository.readAllDiaryEntries
        getUsername = repository.getUsername
        getPassword = repository.getPassword
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

    /**
     * This method updates the entry in database
        and sets username field to given parameter value
    */
    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setUsername(username)
        }
    }

    /**
     * This method updates the entry in database
        and sets password field to given parameter value
    */
    fun setPassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setPassword(password)
        }
    }

    /**
     * This method creates a new entry in database with provided values
        of username and password parameters
    */
    fun createCredentials(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createCredentials(username, password)
        }
    }

    fun setDefaultSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDefaultSettings()
        }
    }
}