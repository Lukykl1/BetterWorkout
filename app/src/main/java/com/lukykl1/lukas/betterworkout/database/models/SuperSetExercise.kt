package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [(ForeignKey(
        entity = Exercise::class,
        parentColumns = ["uid"],
        childColumns = ["exercise1Id"]
    )), (ForeignKey(
        entity = Exercise::class,
        parentColumns = ["uid"],
        childColumns = ["exercise2Id"]
    ))]
)
data class SuperSetExercise(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "exercise1Id") var exercise1Id: Int,
    @ColumnInfo(name = "exercise2Id") var exercise2Id: Int
)