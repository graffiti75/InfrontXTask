package br.android.cericatto.infrontxtask.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.data.fixture.FixtureItem
import br.android.cericatto.infrontxtask.databinding.ItemFixtureBinding
import br.android.cericatto.infrontxtask.util.dayOfMonth
import br.android.cericatto.infrontxtask.util.formatDateToAdapter

class FixturesAdapter : ListAdapter<FixtureItem, FixturesViewHolder>(FixturesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFixtureBinding.inflate(layoutInflater, parent, false)
        return FixturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvCompetitionName.text = item.competitionStage.competition.name
        holder.binding.tvCompetitionName.setTypeface(null, Typeface.BOLD)

        holder.binding.tvHomeTeamName.text = item.homeTeam.name
        holder.binding.tvAwayTeamName.text = item.awayTeam.name

        setDateTime(holder, item)
    }

    private fun setDateTime(holder: FixturesViewHolder, item: FixtureItem) {
        val context = holder.binding.root.context
        holder.binding.tvDayMonth.text = item.date.dayOfMonth()
        holder.binding.tvVenueName.text = "${item.venue.name} | ${item.date.formatDateToAdapter(context)}"
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