package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(entity = Category::class, parentColumns = ["uid"], childColumns = ["categoryId"]))])
data class ExerciseType(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "categoryId") var categoryId: Int

)