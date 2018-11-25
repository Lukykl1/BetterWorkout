package com.lukykl1.lukas.betterworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukykl1.lukas.betterworkout.database.models.Workout
import com.lukykl1.lukas.betterworkout.viewmodel.WorkoutListViewModel
import kotlinx.android.synthetic.main.exercise_list_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class WorkoutListFragment : Fragment() {

    companion object {
        fun newInstance() = WorkoutListFragment()
    }

    private val workoutListViewModel: WorkoutListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.workout_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = recyclerview
        val adapter = WorkoutListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        workoutListViewModel.allWorkoutsWithExercise.observe(this, Observer { workout ->
            // Update the cached copy of the words in the adapter.
            workout?.let { adapter.setWorkout(it) }
        })

        fab.setOnClickListener {
            GlobalScope.launch {
                val newId = workoutListViewModel.insert(Workout(0, "Workout", Date(), null))
                CoroutineScope(Dispatchers.Main).launch {
                    val action = WorkoutListFragmentDirections.actionHomeDestToExerciseListFragment(newId)
                    findNavController().navigate(action)
                }
            }
        }
    }


}
