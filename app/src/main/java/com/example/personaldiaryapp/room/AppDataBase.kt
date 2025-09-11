package com.example.personaldiaryapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DiaryEntry::class, ApplicationSettings::class],
    version = 12,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
//    abstract fun userDao(): AppSettingsDao
    abstract fun appSettingsDao(): ApplicationSettingsDao
    abstract fun diaryEntryDao(): DiaryEntryDao

    companion object {
        private var INSTANCE: AppDataBase?=null

        fun getDataBase( context: Context): AppDataBase
        {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context, AppDataBase::class.java, "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}