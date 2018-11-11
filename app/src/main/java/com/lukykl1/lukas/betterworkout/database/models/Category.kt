package com.lukykl1.lukas.betterworkout.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String
)