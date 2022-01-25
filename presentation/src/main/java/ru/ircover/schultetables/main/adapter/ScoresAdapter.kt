package ru.ircover.schultetables.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ircover.schultetables.R
import ru.ircover.schultetables.databinding.ListScoresItemBinding
import ru.ircover.schultetables.domain.SchulteTableScore
import java.text.DateFormat

class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ListScoresItemBinding.bind(itemView)
    constructor(parent: ViewGroup)
            : this(LayoutInflater.from(parent.context).inflate(R.layout.list_scores_item,
                                                               parent,
                                                               false))

    fun bind(score: SchulteTableScore) {
        binding.tvDuration.text = getString(R.string.score_duration, score.duration / 1000f)
        binding.tvDate.text = DateFormat.getDateTimeInstance().format(score.dateTime.time)
    }

    private fun getString(@StringRes resId: Int, vararg args: Any) =
        itemView.context.getString(resId, *args)
}

class ScoresAdapter : ListAdapter<SchulteTableScore, ScoresViewHolder>(ScoresDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ScoresViewHolder(parent)

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class ScoresDiffCallback : DiffUtil.ItemCallback<SchulteTableScore>() {
    override fun areItemsTheSame(oldItem: SchulteTableScore, newItem: SchulteTableScore) =
        oldItem.dateTime == newItem.dateTime

    override fun areContentsTheSame(oldItem: SchulteTableScore, newItem: SchulteTableScore) =
        oldItem == newItem
}