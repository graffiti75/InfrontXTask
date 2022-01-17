package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.databinding.ItemFixtureBinding
import br.android.cericatto.infrontxtask.databinding.ItemTitleBinding
import br.android.cericatto.infrontxtask.util.dayOfMonth
import br.android.cericatto.infrontxtask.util.formatDateToAdapter
import br.android.cericatto.infrontxtask.util.formattedMonthYear
import br.android.cericatto.infrontxtask.util.weekday

sealed class FixturesRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemTitleBinding) : FixturesRecyclerViewHolder(binding){
        fun bind(fixtureTitle: FixturesRecyclerViewItem.Title) {
            val title = fixtureTitle.title
            val format = "${title?.substring(0, 4)}-${title?.substring(4, title.length)}"
            val formattedTitle = format.formattedMonthYear(binding.root.context)
            binding.tvTitle.text = formattedTitle
        }
    }

    class FixturesViewHolder(private val binding: ItemFixtureBinding) : FixturesRecyclerViewHolder(binding){
        fun bind(fixture: FixturesRecyclerViewItem.Fixture) {
            binding.tvCompetitionName.text = fixture.competitionStage?.competition?.name
            binding.tvCompetitionName.setTypeface(null, Typeface.BOLD)

            binding.tvHomeTeamName.text = fixture.homeTeam?.name
            binding.tvAwayTeamName.text = fixture.awayTeam?.name

            if (fixture.state == binding.root.context.getString(R.string.postponed))
                binding.tvPostponed.visibility = View.VISIBLE
            else
                binding.tvPostponed.visibility = View.GONE

            setDateTime(binding, fixture)
        }

        private fun setDateTime(binding: ItemFixtureBinding, fixture: FixturesRecyclerViewItem.Fixture) {
            val context = binding.root.context
            binding.tvDayMonth.text = fixture.date?.dayOfMonth()

            binding.tvVenueName.apply {
                val string = "${fixture.venue!!.name} | ${fixture.date?.formatDateToAdapter(context)}"
                val spannedString: Spannable = SpannableString(string)
                val startIndex = fixture.venue!!.name.length + 3
                val endIndex = string.length
                spannedString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.red_700)),
                    startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannedString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                text = spannedString
            }
            binding.tvDayWeek.text = fixture.date?.weekday(context)
        }
    }
}