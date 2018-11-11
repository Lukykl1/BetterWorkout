package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "start") var start: Date?,
    @ColumnInfo(name = "end") var end: Date?

)