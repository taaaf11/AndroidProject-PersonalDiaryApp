package com.example.personaldiaryapp.room

import android.app.DirectAction
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Date

@Parcelize
@Entity(
    tableName = "diary_entrys_table"
)
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true)
    var diaryEntryId: Int,
    var title: String,
    var content: String,
    var dateCreated: Date = Date()
//    var timeForNotification: Date? = null
): Parcelable


@Dao
interface DiaryEntryDao {

    @Query("Select * from diary_entrys_table")
    fun readAllDiaryEntrys(): LiveData<List<DiaryEntry>>

    @Insert
    suspend fun addDiaryEntry(entry: DiaryEntry)

    @Delete
    suspend fun deleteDiaryEntry(entry: DiaryEntry)

    @Update
    suspend fun updateDiaryEntry(entry: DiaryEntry)
}
