package com.lukykl1.lukas.betterworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.database.models.Set
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SetListAdapter internal constructor(
    context: Context, val setListViewModel: SetListViewModel
) : RecyclerView.Adapter<SetListAdapter.SetViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sets = emptyList<Set>() // Cached copy of words

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repsView: TextView = itemView.findViewById(R.id.Reps)
        val weightView: TextView = itemView.findViewById(R.id.Weigth)
        val descriptionView: TextView = itemView.findViewById(R.id.descriptionView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_set_item, parent, false)
        return SetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = sets[position]
        holder.repsView.text = current.reps.toString()
        holder.weightView.text = current.weight.toString()
        holder.repsView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val converted = holder.repsView.text.toString().toIntOrNull()
                if (converted != null && converted != current.reps) {
                    current.reps = converted
                    setListViewModel.updateSet(current)
                }
            }
        }
        holder.deleteButton.setOnClickListener {
            GlobalScope.launch { setListViewModel.delete(current) }
        }
        holder.descriptionView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val converted = holder.descriptionView.text.toString()
                if (converted != current.comment) {
                    current.comment = converted
                    setListViewModel.updateSet(current)
                }
            }
        }
        holder.weightView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val converted = holder.weightView.text.toString().toDoubleOrNull()
                if (converted != null && converted != current.weight) {
                    current.weight = converted
                    setListViewModel.updateSet(current)
                }
            }
        }
    }

    internal fun setSets(sets: List<Set>) {
        this.sets = sets
        notifyDataSetChanged()
    }

    override fun getItemCount() = sets.size
}