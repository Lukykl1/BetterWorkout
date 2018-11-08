package com.lukykl1.lukas.betterworkout.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseTypeDao : BaseDao<ExerciseType> {
    @Query("SELECT * FROM exerciseType")
    fun getAll(): List<ExerciseType>

    @Query("SELECT * FROM exerciseType WHERE uid IN (:excerciseTypeIds)")
    fun loadAllByIds(excerciseTypeIds: IntArray): List<ExerciseType>

    @Query("SELECT * FROM exerciseType WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): ExerciseType

}