package com.lukykl1.lukas.betterworkout.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lukykl1.lukas.betterworkout.database.dao.SetDao


interface ISetRepository {
    val allExercise: LiveData<List<com.lukykl1.lukas.betterworkout.database.models.Set>>
    @WorkerThread
    suspend fun insert(set: com.lukykl1.lukas.betterworkout.database.models.Set)

    @WorkerThread
    suspend fun update(set: com.lukykl1.lukas.betterworkout.database.models.Set)
}

class SetRepository constructor(private val setDao: SetDao) : ISetRepository {
    override val allExercise: LiveData<List<com.lukykl1.lukas.betterworkout.database.models.Set>> = setDao.getLiveAll()

    @WorkerThread
    override suspend fun insert(set: com.lukykl1.lukas.betterworkout.database.models.Set) {
        setDao.insert(set)
    }

    fun getByExerciseId(exerciseId: Int): LiveData<List<com.lukykl1.lukas.betterworkout.database.models.Set>> {
        return setDao.getLiveAllById(exerciseId)
    }

    @WorkerThread
    override suspend fun update(set: com.lukykl1.lukas.betterworkout.database.models.Set) {
        setDao.update(set)
    }
}