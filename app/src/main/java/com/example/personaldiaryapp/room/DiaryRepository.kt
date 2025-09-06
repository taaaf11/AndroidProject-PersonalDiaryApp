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
//    val getLoggedInStatus: LiveData<Boolean> = appSettingsDao.getLoggedInStatus()
//    val getThemeMode: LiveData<Boolean> = appSettingsDao.getThemeMode()

    suspend fun addDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.addDiaryEntry(entry)
    }

    suspend fun updateDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.updateDiaryEntry(entry)
    }

    suspend fun deleteDiaryEntry(entry: DiaryEntry) {
        diaryEntryDao.deleteDiaryEntry(entry)
    }

//    suspend fun verifyCredentials(username: String, password:String): Boolean
//    {
//        val savedUsername = appSettingsDao.getUsername()
//        val savedPassword = appSettingsDao.getPassword()
//        return (username == savedUsername && password == savedPassword)
//    }

    suspend fun setUsername(username: String) {
        appSettingsDao.setUsername(username)
    }

    suspend fun setPassword(password: String) {
        appSettingsDao.setPassword(password)
    }

    suspend fun setCredientials(username: String, password: String) {
        appSettingsDao.setCredentials(username, password)
    }

//    suspend fun setLoggedInStatus(isLoggedIn: Boolean) {
//        appSettingsDao.setLoggedInStatus(isLoggedIn)
//    }

//    suspend fun setLightMode() {
//        appSettingsDao.setLightMode()
//    }
//
//    suspend fun setDarkMode() {
//        appSettingsDao.setDarkMode()
//    }

    suspend fun setDefaultSettings() {
        appSettingsDao.setDefaultSettings()
    }


//    suspend fun addUser(appSettings: AppSettings) {
//        appSettingsDao.addUser(appSettings)
//    }
}