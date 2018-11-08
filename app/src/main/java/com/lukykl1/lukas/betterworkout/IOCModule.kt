package com.lukykl1.lukas.betterworkout

import androidx.room.Room
import com.lukykl1.lukas.betterworkout.database.AppDatabase
import com.lukykl1.lukas.betterworkout.database.ExerciseDao
import com.lukykl1.lukas.betterworkout.repository.ExerciseRepository
import com.lukykl1.lukas.betterworkout.repository.IExerciseRepository
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val IOCModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "weather-db")
            .build()
    }
    single {
        get<AppDatabase>().excerciseDao()
    }
    single {
        ExerciseRepository(get())
    }
    viewModel { ExerciseViewModel(get()) }
}