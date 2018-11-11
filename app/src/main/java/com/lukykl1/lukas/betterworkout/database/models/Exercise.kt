package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [(ForeignKey(
        entity = ExerciseType::class,
        parentColumns = ["uid"],
        childColumns = ["exerciseTypeId"]
    )), (ForeignKey(
        entity = Workout::class,
        parentColumns = ["uid"],
        childColumns = ["workoutId"]
    ))]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "workoutId") var workoutId: Int,
    @ColumnInfo(name = "exerciseTypeId") var exerciseTypeId: Int?
)