package com.lukykl1.lukas.betterworkout.database.dao


import androidx.room.Dao
import androidx.room.Query
import com.lukykl1.lukas.betterworkout.database.models.Set

@Dao
interface SetDao : BaseDao<Set> {
    @Query("SELECT * FROM `set`")
    fun getAll(): List<Set>

    @Query("SELECT * FROM `Set` WHERE uid IN (:setIds)")
    fun loadAllByIds(setIds: IntArray): List<Set>


}