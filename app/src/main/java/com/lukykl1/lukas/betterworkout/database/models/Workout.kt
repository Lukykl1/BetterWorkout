package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lukykl1.lukas.betterworkout.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "start") @Serializable(with = DateSerializer::class) var start: Date,
    @ColumnInfo(name = "end") @Serializable(with = DateSerializer::class) var end: Date?
)
