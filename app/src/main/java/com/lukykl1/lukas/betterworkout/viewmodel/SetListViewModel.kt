package com.lukykl1.lukas.betterworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lukykl1.lukas.betterworkout.database.models.Set
import com.lukykl1.lukas.betterworkout.repository.SetRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetListViewModel(private val repository: SetRepository) : ViewModel() {
    fun getAllSetsFoExercise(exerciseId: Int): LiveData<List<Set>> {
        return repository.getByExerciseId(exerciseId)
    }

    fun insert(set: Set) {
        GlobalScope.launch { repository.insert(set) }
    }

    fun updateSet(set: Set) {
        GlobalScope.launch { repository.update(set) }
    }

    fun delete(set: Set) {

        GlobalScope.launch { repository.delete(set) }
    }
}
