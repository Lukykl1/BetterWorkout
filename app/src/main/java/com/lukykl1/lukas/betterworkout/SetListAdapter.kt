package com.lukykl1.lukas.betterworkout

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukykl1.lukas.betterworkout.database.models.Set
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel


class SetListAdapter internal constructor(
    context: Context, val setListViewModel: SetListViewModel
) : RecyclerView.Adapter<SetListAdapter.SetViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sets = emptyList<Set>() // Cached copy of words

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repsView: TextView = itemView.findViewById(R.id.Reps)
        val weightView: TextView = itemView.findViewById(R.id.Weigth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_set_item, parent, false)
        return SetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = sets[position]
        holder.repsView.text = current.reps.toString()
        holder.weightView.text = current.weight.toString()

        holder.repsView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val converted = s.toString().toIntOrNull()
                if (converted != null && current.reps != converted) {
                    current.reps = converted
                    setListViewModel.updateSet(current)
                }
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
        holder.weightView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val converted = s.toString().toDoubleOrNull()
                if (converted != null && current.weight != converted) {
                    current.weight = converted
                    setListViewModel.updateSet(current)
                }
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

    internal fun setSets(sets: List<Set>) {
        this.sets = sets
        notifyDataSetChanged()
    }

    override fun getItemCount() = sets.size
}