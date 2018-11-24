package com.lukykl1.lukas.betterworkout.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lukykl1.lukas.betterworkout.database.dao.*
import com.lukykl1.lukas.betterworkout.database.models.*
import com.lukykl1.lukas.betterworkout.database.models.Set


@Database(
    entities = [Category::class, Exercise::class, ExerciseType::class, Set::class, SuperSetExercise::class, Workout::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseTypeDao(): ExerciseTypeDao
    abstract fun setDao(): SetDao
    abstract fun superSetExerciseDao(): SuperSetExerciseDao
    abstract fun workoutDao(): WorkoutDao
}

