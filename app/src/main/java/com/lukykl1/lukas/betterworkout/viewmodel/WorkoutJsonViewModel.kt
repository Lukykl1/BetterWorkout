package com.lukykl1.lukas.betterworkout.viewmodel

import androidx.lifecycle.ViewModel
import com.lukykl1.lukas.betterworkout.pojo.WorkoutWithExercises
import com.lukykl1.lukas.betterworkout.repository.ExerciseRepository
import com.lukykl1.lukas.betterworkout.repository.WorkoutRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JSON

class WorkoutJsonViewModel(
    private val repository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    fun insertNewWorkout(json: String) {
        GlobalScope.launch {
            val deserialized = JSON.parse(WorkoutWithExercises.serializer(), json)
            deserialized.workout?.uid = 0
            repository.insert(deserialized.workout!!)
            for (exercise in deserialized.exercises!!) {
                exercise.uid = 0
                exerciseRepository.insert(exercise)
            }
        }

    }
}
