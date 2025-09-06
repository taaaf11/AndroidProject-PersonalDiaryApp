package com.example.personaldiaryapp.room

import androidx.lifecycle.LiveData

class DiaryRepository(
    private val appSettingsDao: ApplicationSettingsDao,
    private val diaryEntryDao: DiaryEntryDao,
)
{
    val readAllDiaryEntries: LiveData<List<DiaryEntry>> = diaryEntryDao.readAllDiaryEntrys()
    val getUsername: LiveData<String> = appSettingsDao.getUsername()
    val getPassword: LiveData<String> = appSettingsDao.getPassword()

    suspend fun addDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.addDiaryEntry(entry)
    }

    suspend fun updateDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.updateDiaryEntry(entry)
    }

    suspend fun deleteDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.deleteDiaryEntry(entry)
    }

    suspend fun setUsername(username: String) {
        appSettingsDao.setUsername(username)
    }

    suspend fun setPassword(password: String) {
        appSettingsDao.setPassword(password)
    }

    suspend fun setCredientials(username: String, password: String) {
        appSettingsDao.setCredentials(username, password)
    }

    suspend fun setDefaultSettings() {
        appSettingsDao.setDefaultSettings()
    }
}