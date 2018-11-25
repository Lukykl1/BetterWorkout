package com.lukykl1.lukas.betterworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.database.models.Set
import com.lukykl1.lukas.betterworkout.databinding.RecyclerviewExerciseItemBinding
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseListViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel


class ExerciseListAdapter internal constructor(
    val context: Context,
    val setListViewModel: SetListViewModel,
    val exerciseListViewModel: ExerciseListViewModel,
    val owner: LifecycleOwner
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exercises = emptyList<Exercise>() // Cached copy of words

    inner class ExerciseViewHolder(val binding: RecyclerviewExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        parent.context;
        val binding: RecyclerviewExerciseItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.recyclerview_exercise_item, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.exercise = exercise
        holder.binding.editText.setText(exercise.name)

        val adapter = SetListAdapter(context, setListViewModel)
        holder.binding.recyclerview.adapter = adapter
        holder.binding.recyclerview.layoutManager = LinearLayoutManager(context)
        setListViewModel.getAllSetsFoExercise(exercise.uid).observe(owner, Observer { set ->
            // Update the cached copy of the words in the adapter.
            set?.let { adapter.setSets(it) }
        })
        holder.binding.AddSetButton.setOnClickListener {
            val set = Set(
                0,
                "New exercise",
                0,
                0.0,
                exercise.uid
            )
            setListViewModel.insert(set)
        }
        holder.binding.deleteExercise.setOnClickListener {
            exerciseListViewModel.delete(exercise)
        }
        holder.binding.editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val converted = holder.binding.editText.text.toString()
                if (converted != exercise.name) {
                    exercise.name = holder.binding.editText.text.toString()
                    exerciseListViewModel.updateExercise(exercise)
                }
            }
        }
    }

    internal fun setExercise(exercises: List<Exercise>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }

    override fun getItemCount() = exercises.size
}