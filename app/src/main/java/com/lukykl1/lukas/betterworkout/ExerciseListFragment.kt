package com.lukykl1.lukas.betterworkout

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseListViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel
import kotlinx.android.synthetic.main.exercise_list_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class ExerciseListFragment : Fragment() {

    companion object {
        fun newInstance() = ExerciseListFragment()
    }

    private val exerciseListViewModel: ExerciseListViewModel by viewModel()
    private val setListViewModel: SetListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_list_fragment, container, false)
    }

    var workoutId: Long = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        workoutId = ExerciseListFragmentArgs.fromBundle(arguments).workoutId
        exerciseListViewModel.setWorkoutId(workoutId, WorkoutNameView)


        val recyclerView = recyclerview
        val adapter = ExerciseListAdapter(context!!, setListViewModel, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        exerciseListViewModel.getExercisesForWorkout(workoutId).observe(this, Observer { exercises ->
            // Update the cached copy of the words in the adapter.
            exercises?.let { adapter.setExercise(it) }
        })

        fab.setOnClickListener {
            val exercise = Exercise(
                0,
                "New exercise",
                workoutId.toInt(),
                null
            )
            exerciseListViewModel.insert(exercise)
        }

        WorkoutNameView.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                exerciseListViewModel.workout?.name = s.toString()
                exerciseListViewModel.updateWorkout()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })

    }


}
