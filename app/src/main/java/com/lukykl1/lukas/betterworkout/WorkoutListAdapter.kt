package com.lukykl1.lukas.betterworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.databinding.RecyclerviewWorkoutItemBinding
import com.lukykl1.lukas.betterworkout.pojo.WorkoutWithExercises


class WorkoutListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var workouts = emptyList<WorkoutWithExercises>()

    inner class WorkoutViewHolder(val binding: RecyclerviewWorkoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding: RecyclerviewWorkoutItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.recyclerview_workout_item, parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.binding.workoutWithExercises = workouts[position]
        holder.binding.eventHandler = EventHandler()
    }

    inner class EventHandler {
        fun goToWorkout(view: View, workoutId: Int, workoutName: String) {
            val action = WorkoutListFragmentDirections.actionHomeDestToExerciseListFragment(workoutId.toLong())
            view.findNavController().navigate(action)
        }
    }

    fun setWorkout(workouts: List<WorkoutWithExercises>) {
        this.workouts = workouts
        notifyDataSetChanged()
    }

    override fun getItemCount() = workouts.size
}