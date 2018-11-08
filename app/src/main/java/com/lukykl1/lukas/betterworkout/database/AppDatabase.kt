package com.lukykl1.lukas.betterworkout.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Category::class, Exercise::class, ExerciseType::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun excerciseDao(): ExerciseDao
    abstract fun excerciseTypeDao(): ExerciseTypeDao
}