package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.databinding.ItemResultBinding
import br.android.cericatto.infrontxtask.databinding.ItemTitleBinding
import br.android.cericatto.infrontxtask.util.formatDateToAdapter
import br.android.cericatto.infrontxtask.util.formattedMonthYear

sealed class ResultsRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemTitleBinding) : ResultsRecyclerViewHolder(binding){
        fun bind(resultTitle: ResultsRecyclerViewItem.Title) {
            val title = resultTitle.title
            val format = "${title.substring(0, 4)}-${title.substring(4, title.length)}"
            val formattedTitle = format.formattedMonthYear(binding.root.context)
            binding.tvTitle.text = formattedTitle
        }
    }

    class ResultsViewHolder(private val binding: ItemResultBinding) : ResultsRecyclerViewHolder(binding){
        fun bind(result: ResultsRecyclerViewItem.Result) {
            binding.tvCompetitionName.text = result.competitionStage.competition.name
            binding.tvCompetitionName.setTypeface(null, Typeface.BOLD)
            setHomeAwayTeams(binding, result)
        }

        private fun setHomeAwayTeams(binding: ItemResultBinding, result: ResultsRecyclerViewItem.Result) {
            val context = binding.root.context
            val homeScore = result.score.home
            val awayScore = result.score.away

            binding.tvHomeTeamName.text = result.homeTeam.name
            binding.tvAwayTeamName.text = result.awayTeam.name
            when {
                homeScore > awayScore -> {
                    binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_600))
                    binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                }
                awayScore > homeScore -> {
                    binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                    binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_600))
                }
                else -> {
                    binding.tvScoreHomeTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                    binding.tvScoreAwayTeam.setTextColor(ContextCompat.getColor(context, R.color.blue_1000))
                }
            }
            binding.tvScoreHomeTeam.text = homeScore.toString()
            binding.tvScoreAwayTeam.text = awayScore.toString()

            binding.tvVenueName.text = "${result.venue.name} | ${result.date.formatDateToAdapter(context)}"
        }
    }
}