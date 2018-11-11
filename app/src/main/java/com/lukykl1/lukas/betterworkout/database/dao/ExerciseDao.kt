package com.lukykl1.lukas.betterworkout.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lukykl1.lukas.betterworkout.database.models.Exercise

@Dao
interface ExerciseDao : BaseDao<Exercise> {
    @Query("SELECT * FROM exercise")
    fun getAll(): List<Exercise>

    @Query("SELECT * FROM exercise")
    fun getLiveAll(): LiveData<List<Exercise>>


    @Query("SELECT * FROM exercise WHERE uid IN (:excerciseIds)")
    fun loadAllByIds(excerciseIds: IntArray): List<Exercise>

    @Query("SELECT * FROM exercise WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Exercise

}