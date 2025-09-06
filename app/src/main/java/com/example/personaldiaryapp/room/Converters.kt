package com.example.personaldiaryapp.room

import androidx.room.TypeConverter
import java.util.Date


// from chatgpt and
// official docs: https://developer.android.com/training/data-storage/room/referencing-data
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}