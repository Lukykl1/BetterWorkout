package com.lukykl1.lukas.betterworkout.database.dao


import androidx.room.Dao
import androidx.room.Query
import com.lukykl1.lukas.betterworkout.database.models.Workout

@Dao
interface WorkoutDao : BaseDao<Workout> {
    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

    @Query("SELECT * FROM Workout WHERE uid IN (:workoutIds)")
    fun loadAllByIds(workoutIds: IntArray): List<Workout>

    @Query("SELECT * FROM Workout WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Workout
}