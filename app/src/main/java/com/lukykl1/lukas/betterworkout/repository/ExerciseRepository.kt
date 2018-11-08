package com.lukykl1.lukas.betterworkout.repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lukykl1.lukas.betterworkout.database.Exercise
import com.lukykl1.lukas.betterworkout.database.ExerciseDao


interface IExerciseRepository {
    val allExercise: LiveData<List<Exercise>>
    @WorkerThread
    suspend fun insert(exercise: Exercise)
}

class ExerciseRepository constructor(private val exerciseDao: ExerciseDao) : IExerciseRepository {
    override val allExercise: LiveData<List<Exercise>> = exerciseDao.getLiveAll()

    @WorkerThread
    override suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }
}