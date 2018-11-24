package com.lukykl1.lukas.betterworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lukykl1.lukas.betterworkout.database.models.Workout
import com.lukykl1.lukas.betterworkout.pojo.WorkoutWithExercises
import com.lukykl1.lukas.betterworkout.repository.WorkoutRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class WorkoutListViewModel(private val repository: WorkoutRepository) : ViewModel() {
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts
    val allWorkoutsWithExercise: LiveData<List<WorkoutWithExercises>> = repository.allWorkoutsWithExercise

    suspend fun insert(workout: Workout): Long {
        return GlobalScope.async {
            repository.insert(workout)
        }.await()
    }
}
