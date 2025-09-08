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

    /**
     * This method updates the entry in database
    and sets username field to given parameter value
     */
    suspend fun setUsername(username: String) {
        appSettingsDao.setUsername(username)
    }

    /**
     * This method updates the entry in database
    and sets password field to given parameter value
     */
    suspend fun setPassword(password: String) {
        appSettingsDao.setPassword(password)
    }

    /**
     * This method updates the entry in database
    and sets username field to given parameter value
     */
    suspend fun createCredentials(username: String, password: String) {
        appSettingsDao.createCredentials(username, password)
    }

    suspend fun setDefaultSettings() {
        appSettingsDao.setDefaultSettings()
    }
}