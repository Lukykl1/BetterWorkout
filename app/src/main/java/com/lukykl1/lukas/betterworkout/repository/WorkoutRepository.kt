package com.lukykl1.lukas.betterworkout.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lukykl1.lukas.betterworkout.database.dao.WorkoutDao
import com.lukykl1.lukas.betterworkout.database.models.Workout
import com.lukykl1.lukas.betterworkout.pojo.WorkoutWithExercises

interface IWorkoutRepository {
    val allWorkouts: LiveData<List<Workout>>
    val allWorkoutsWithExercise: LiveData<List<WorkoutWithExercises>>
    @WorkerThread
    suspend fun insert(exercise: Workout): Long

    @WorkerThread
    suspend fun getById(workoutId: Long): Workout

    @WorkerThread
    fun update(workout: Workout?)

    @WorkerThread
    fun delete(workout: Workout)
}

class WorkoutRepository constructor(private val workoutDao: WorkoutDao) : IWorkoutRepository {
    override val allWorkouts: LiveData<List<Workout>> = workoutDao.getLiveAll()
    override val allWorkoutsWithExercise: LiveData<List<WorkoutWithExercises>> = workoutDao.getLiveAllWithExercise()

    @WorkerThread
    override suspend fun insert(exercise: Workout): Long {
        return workoutDao.insert(exercise)
    }

    @WorkerThread
    override suspend fun getById(workoutId: Long): Workout {
        return workoutDao.getById(workoutId)
    }

    @WorkerThread
    override fun update(workout: Workout?) {
        if (workout != null) {
            workoutDao.update(workout)
        }
    }

    @WorkerThread
    override fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }
}