package com.lukykl1.lukas.betterworkout

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.databinding.RecyclerviewWorkoutItemBinding
import com.lukykl1.lukas.betterworkout.pojo.WorkoutWithExercises
import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.json.JSON
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WorkoutListAdapter internal constructor(
    val context: Context
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

        fun share(view: View, workoutId: Int) {
            val serialized: String =
                JSON.stringify(WorkoutWithExercises.serializer(), workouts.single { it.workout?.uid == workoutId })
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, serialized)
                type = "application/json"
            }
            startActivity(context, Intent.createChooser(sendIntent, "Share your workout"), null)
        }
    }

    fun setWorkout(workouts: List<WorkoutWithExercises>) {
        this.workouts = workouts
        notifyDataSetChanged()
    }

    override fun getItemCount() = workouts.size

}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")

    override val descriptor: SerialDescriptor =
        SerialClassDescImpl("java.util.Date")

    @ImplicitReflectionSerializer
    override fun serialize(output: Encoder, obj: Date) {
        output.encode(df.format(obj))
    }

    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): Date {
        return df.parse(input.decode())
    }
}