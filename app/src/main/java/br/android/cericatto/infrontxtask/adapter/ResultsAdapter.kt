package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.databinding.ItemResultBinding
import br.android.cericatto.infrontxtask.util.formatDateToAdapter

class ResultsAdapter : ListAdapter<ResultItem, ResultsViewHolder>(ResultsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(layoutInflater, parent, false)
        return ResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvCompetitionName.text = item.competitionStage.competition.name
        holder.binding.tvCompetitionName.setTypeface(null, Typeface.BOLD)

        setHomeAwayTeams(holder, item)
    }

    private fun setHomeAwayTeams(holder: ResultsViewHolder, item: ResultItem) {
        val context = holder.binding.root.context
        val homeScore = item.score.home
        val awayScore = item.score.away

        holder.binding.tvHomeTeamName.text = item.homeTeam.name
        holder.binding.tvAwayTeamName.text = item.awayTeam.name
        when {
            homeScore > awayScore -> {
                holder.binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_600))
                holder.binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
            }
            awayScore > homeScore -> {
                holder.binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                holder.binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_600))
            }
            else -> {
                holder.binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                holder.binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
            }
        }
        holder.binding.tvScoreHomeTeam.text = homeScore.toString()
        holder.binding.tvScoreAwayTeam.text = awayScore.toString()

        holder.binding.tvVenueName.text = "${item.venue.name} | ${item.date.formatDateToAdapter(context)}"
    }
}

class ResultsViewHolder(val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root)

class ResultsDiffCallback : DiffUtil.ItemCallback<ResultItem>() {
    override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem == newItem
    }
}