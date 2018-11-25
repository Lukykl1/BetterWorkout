package com.lukykl1.lukas.betterworkout.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lukykl1.lukas.betterworkout.database.dao.ExerciseDao
import com.lukykl1.lukas.betterworkout.database.models.Exercise


interface IExerciseRepository {
    val allExercise: LiveData<List<Exercise>>
    @WorkerThread
    suspend fun insert(exercise: Exercise)

    @WorkerThread
    fun update(exercise: Exercise)

    fun allExerciseForWorkout(workoutId: Long): LiveData<List<Exercise>>
    @WorkerThread
    fun delete(exercise: Exercise)
}

class ExerciseRepository constructor(private val exerciseDao: ExerciseDao) : IExerciseRepository {
    override val allExercise: LiveData<List<Exercise>> = exerciseDao.getLiveAll()

    @WorkerThread
    override suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    override fun allExerciseForWorkout(workoutId: Long): LiveData<List<Exercise>> {
        return exerciseDao.getAllForWorkout(workoutId)
    }

    @WorkerThread
    override fun update(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    @WorkerThread
    override fun delete(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }
}