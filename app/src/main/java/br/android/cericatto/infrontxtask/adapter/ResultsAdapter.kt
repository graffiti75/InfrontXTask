package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.databinding.ItemResultBinding

class ResultsAdapter : ListAdapter<ResultItem, ResultsViewHolder>(ResultsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(layoutInflater, parent, false)
        return ResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvCompetitionName.text = item.competitionStage.competition.name
        holder.binding.tvVenueName.text = item.venue.name
        // Input: 2019-02-02T15:00:00.000Z
        // Output: Feb 2, 2019 at 15:00
        val date = ""
        setHomeAwayTeams(holder, item)
    }

    private fun setHomeAwayTeams(holder: ResultsViewHolder, item: ResultItem) {
        val context = holder.binding.root.context
        val homeScore = item.score.home
        val awayScore = item.score.away

        holder.binding.tvHomeTeamName.text = item.homeTeam.name
        holder.binding.tvAwayTeamName.text = item.awayTeam.name
        if (homeScore > awayScore) {
            holder.binding.tvHomeTeamName.setTextSize(TypedValue.COMPLEX_UNIT_SP,22f)
            holder.binding.tvAwayTeamName.setTextSize(TypedValue.COMPLEX_UNIT_SP,20f)
            holder.binding.tvHomeTeamName.setTypeface(null, Typeface.BOLD)

            holder.binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_700))
            holder.binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.black))
        } else if (awayScore > homeScore) {
            holder.binding.tvHomeTeamName.setTextSize(TypedValue.COMPLEX_UNIT_SP,20f)
            holder.binding.tvAwayTeamName.setTextSize(TypedValue.COMPLEX_UNIT_SP,22f)
            holder.binding.tvAwayTeamName.setTypeface(null, Typeface.BOLD)

            holder.binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_700))
            holder.binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
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