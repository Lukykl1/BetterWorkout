package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [(ForeignKey(
        entity = ExerciseType::class,
        parentColumns = ["uid"],
        childColumns = ["exerciseId"]
    ))]
)
data class Set(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "comment") var comment: String,
    @ColumnInfo(name = "reps") var reps: Int,
    @ColumnInfo(name = "weight") var weight: Double,
    @ColumnInfo(name = "exerciseId") var exerciseId: Int
)