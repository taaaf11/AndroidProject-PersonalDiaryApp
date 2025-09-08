package com.example.personaldiaryapp.room

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity( tableName = "users_table" )
data class AppSettings(
    @PrimaryKey(autoGenerate = true)
    var settingId: Int,
    var username: String,
    var password: String,
    var isLightMode: Boolean = true,
    var isLoggedIn: Boolean = false
): Parcelable


@Parcelize
@Entity( tableName = "application_settings_table")
data class ApplicationSettings(
    @PrimaryKey(autoGenerate = true)
    var settingId: Int,
    var username: String = "",
    var password: String = ""
): Parcelable {
    companion object {
        fun generateDefaultValuesSetting(): ApplicationSettings {
            return ApplicationSettings(0, "", "")
        }
    }
}

@Dao
interface ApplicationSettingsDao {

    @Query("select * from application_settings_table")
    fun readAllSettings(): List<ApplicationSettings>

    /**
     * This method updates the entry in database
    and sets username field to given parameter value
     */
    @Query("update application_settings_table set username = :username")
    suspend fun setUsername(username: String)

    @Query("select username from application_settings_table")
    fun getUsername(): LiveData<String>

    /**
     * This method updates the entry in database
    and sets password field to given parameter value
     */
    @Query("update application_settings_table set password = :password")
    suspend fun setPassword(password: String)

    @Query("select password from application_settings_table")
    fun getPassword(): LiveData<String>

    /**
     * This method updates the entry in database
    and sets username field to given parameter value
     */
    @Query("insert into application_settings_table(username, password) VALUES (:username, :password) ")
    suspend fun createCredentials(username: String, password: String)

    @Insert
    suspend fun setDefaultSettings(defaultSetting: ApplicationSettings = ApplicationSettings.generateDefaultValuesSetting())
}