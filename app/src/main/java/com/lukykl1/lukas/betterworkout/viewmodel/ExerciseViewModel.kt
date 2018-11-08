package com.lukykl1.lukas.betterworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lukykl1.lukas.betterworkout.database.Exercise
import com.lukykl1.lukas.betterworkout.repository.ExerciseRepository
import com.lukykl1.lukas.betterworkout.repository.IExerciseRepository
import kotlinx.coroutines.*

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    val allExcercises: LiveData<List<Exercise>> = repository.allExercise

    fun insert(exercise: Exercise) {
        GlobalScope.launch {
            repository.insert(exercise)
        }
    }
}