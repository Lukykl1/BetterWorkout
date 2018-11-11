package com.lukykl1.lukas.betterworkout.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.lukykl1.lukas.betterworkout.database.models.Category

@Dao
interface CategoryDao : BaseDao<Category> {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE uid IN (:categoryIds)")
    fun loadAllByIds(categoryIds: IntArray): List<Category>

    @Query("SELECT * FROM category WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Category


}