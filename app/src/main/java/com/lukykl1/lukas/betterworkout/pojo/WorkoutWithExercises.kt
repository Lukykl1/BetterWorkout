package com.lukykl1.lukas.betterworkout.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.database.models.Workout
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat

@Serializable
class WorkoutWithExercises {
    @Embedded
    var workout: Workout? = null
    @Relation(parentColumn = "uid", entityColumn = "workoutId", entity = Exercise::class)
    var exercises: List<Exercise>? = null

    fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM")
        return dateFormat.format(workout?.start)

    }

    fun getStartEndString(): String {
        val dateFormat = SimpleDateFormat("H:mm")
        return dateFormat.format(workout?.start) //+ "-" + (if (workout?.start == null) dateFormat.format(workout?.start) else "")
    }
}
