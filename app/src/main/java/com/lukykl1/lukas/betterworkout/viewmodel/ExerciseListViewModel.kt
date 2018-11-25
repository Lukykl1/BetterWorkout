package com.lukykl1.lukas.betterworkout.viewmodel

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.database.models.Workout
import com.lukykl1.lukas.betterworkout.repository.ExerciseRepository
import com.lukykl1.lukas.betterworkout.repository.WorkoutRepository
import kotlinx.coroutines.*

class ExerciseListViewModel(
    private val repository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    val allExcercises: LiveData<List<Exercise>> = repository.allExercise
    var workout: Workout? = null

    fun insert(exercise: Exercise) {
        GlobalScope.launch {
            repository.insert(exercise)
        }
    }

    suspend fun getWorkout(workoutId: Long): Deferred<Workout> {
        return GlobalScope.async {
            workoutRepository.getById(workoutId)
        }
    }

    fun setWorkoutId(workoutId: Long, workoutNameView: EditText) {
        GlobalScope.launch {
            this@ExerciseListViewModel.workout = getWorkout(workoutId).await()
            CoroutineScope(Dispatchers.Main).launch {
                workoutNameView.setText(this@ExerciseListViewModel.workout?.name)
            }
        }
    }

    fun updateWorkout() {
        GlobalScope.launch {
            workoutRepository.update(this@ExerciseListViewModel.workout)
        }
    }

    fun getExercisesForWorkout(workoutId: Long): LiveData<List<Exercise>> {
        return repository.allExerciseForWorkout(workoutId)
    }

    fun updateExercise(exercise: Exercise) {
        GlobalScope.launch {
            repository.update(exercise)
        }
    }

    fun delete(exercise: Exercise) {
        GlobalScope.launch {
            repository.delete(exercise)
        }
    }

    fun deleteById(workoutId: Long) {
        GlobalScope.launch {
            workoutRepository.delete(getWorkout(workoutId).await())
        }
    }
}
