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

    fun setCredentials(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setCredientials(username, password)
        }
    }

    fun setDefaultSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDefaultSettings()
        }
    }
}