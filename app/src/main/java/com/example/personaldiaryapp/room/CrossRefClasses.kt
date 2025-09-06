package com.example.personaldiaryapp.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


@Entity(primaryKeys = ["userId", "diaryEntryId"])
data class UserDiaryEntryCrossRef(
    val userId: Long,
    val diaryEntryId: Long
)

data class UserWithDiaryEntrys(
    @Embedded val appSettings: AppSettings,
    @Relation(
        parentColumn = "userId",
        entityColumn = "diaryEntryId"
    )
    val diaryEntrys: List<DiaryEntry>
)