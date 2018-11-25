package com.lukykl1.lukas.betterworkout

import androidx.room.Room
import com.lukykl1.lukas.betterworkout.database.AppDatabase
import com.lukykl1.lukas.betterworkout.repository.ExerciseRepository
import com.lukykl1.lukas.betterworkout.repository.SetRepository
import com.lukykl1.lukas.betterworkout.repository.WorkoutRepository
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseListViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.WorkoutJsonViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.WorkoutListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val IOCModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "workout-db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<AppDatabase>().exerciseDao()
    }
    single {
        get<AppDatabase>().workoutDao()
    }
    single {
        get<AppDatabase>().setDao()
    }
    single {
        ExerciseRepository(get())
    }
    single {
        SetRepository(get())
    }
    single {
        WorkoutRepository(get())
    }
    viewModel { ExerciseListViewModel(get(), get()) }
    viewModel { WorkoutJsonViewModel(get(), get()) }

    viewModel { WorkoutListViewModel(get()) }
    viewModel { SetListViewModel(get()) }
}