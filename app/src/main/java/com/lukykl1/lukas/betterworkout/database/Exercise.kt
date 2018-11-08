package com.lukykl1.lukas.betterworkout.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [(ForeignKey(
        entity = ExerciseType::class,
        parentColumns = ["uid"],
        childColumns = ["exerciseTypeId"]
    ))]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "exerciseTypeId") var exerciseTypeId: Int?

)