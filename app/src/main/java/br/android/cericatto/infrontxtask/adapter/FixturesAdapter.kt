package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.data.fixture.FixtureItem
import br.android.cericatto.infrontxtask.databinding.ItemFixtureBinding
import br.android.cericatto.infrontxtask.util.dayOfMonth
import br.android.cericatto.infrontxtask.util.formatDateToAdapter
import br.android.cericatto.infrontxtask.util.weekday

class FixturesAdapter : ListAdapter<FixtureItem, FixturesViewHolder>(FixturesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFixtureBinding.inflate(layoutInflater, parent, false)
        return FixturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        holder.binding.tvCompetitionName.text = item.competitionStage.competition.name
        holder.binding.tvCompetitionName.setTypeface(null, Typeface.BOLD)

        holder.binding.tvHomeTeamName.text = item.homeTeam.name
        holder.binding.tvAwayTeamName.text = item.awayTeam.name

        if (item.state == context.getString(R.string.postponed))
            holder.binding.tvPostponed.visibility = View.VISIBLE
        else
            holder.binding.tvPostponed.visibility = View.GONE

        setDateTime(holder, item)
    }

    private fun setDateTime(holder: FixturesViewHolder, item: FixtureItem) {
        val context = holder.binding.root.context
        holder.binding.tvDayMonth.text = item.date.dayOfMonth()

        holder.binding.tvVenueName.apply {
            val string = "${item.venue.name} | ${item.date.formatDateToAdapter(context)}"
            val spannedString: Spannable = SpannableString(string)
            val startIndex = item.venue.name.length + 3
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

        holder.binding.tvDayWeek.text = item.date.weekday(context)
    }
}

class FixturesViewHolder(val binding: ItemFixtureBinding): RecyclerView.ViewHolder(binding.root)

class FixturesDiffCallback : DiffUtil.ItemCallback<FixtureItem>() {
    override fun areItemsTheSame(oldItem: FixtureItem, newItem: FixtureItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FixtureItem, newItem: FixtureItem): Boolean {
        return oldItem == newItem
    }
}