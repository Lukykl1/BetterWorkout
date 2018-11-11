package com.lukykl1.lukas.betterworkout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    companion object {
        const val newExerciseActivityRequestCode = 1
    }

    val exerciseViewModel: ExerciseViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ExerciseListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        exerciseViewModel.allExcercises.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setExercise(it) }
        })
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewExerciseActivity::class.java)
            startActivityForResult(intent, newExerciseActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newExerciseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val exercise = Exercise(
                    0,
                    it.getStringExtra(NewExerciseActivity.EXTRA_REPLY),
                    0,
                    null
                )
                exerciseViewModel.insert(exercise)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}

class ExerciseListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ExerciseListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Exercise>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.name
    }

    internal fun setExercise(words: List<Exercise>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}