package com.lukykl1.lukas.betterworkout.database.dao


import androidx.room.Dao
import androidx.room.Query
import com.lukykl1.lukas.betterworkout.database.models.SuperSetExercise

@Dao
interface SuperSetExerciseDao : BaseDao<SuperSetExercise> {
    @Query("SELECT * FROM SuperSetExercise")
    fun getAll(): List<SuperSetExercise>

    @Query("SELECT * FROM SuperSetExercise WHERE uid IN (:superSetExerciseIds)")
    fun loadAllByIds(superSetExerciseIds: IntArray): List<SuperSetExercise>

    @Query("SELECT * FROM SuperSetExercise WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SuperSetExercise
}