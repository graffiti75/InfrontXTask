package br.android.cericatto.infrontxtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.databinding.ItemFixtureBinding
import br.android.cericatto.infrontxtask.databinding.ItemTitleBinding

class FixturesAdapter : RecyclerView.Adapter<FixturesRecyclerViewHolder>() {

    var items = listOf<FixturesRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesRecyclerViewHolder {
        return when(viewType){
            R.layout.item_title -> FixturesRecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_fixture -> FixturesRecyclerViewHolder.FixturesViewHolder(
                ItemFixtureBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: FixturesRecyclerViewHolder, position: Int) {
        when(holder){
            is FixturesRecyclerViewHolder.TitleViewHolder ->
                holder.bind(items[position] as FixturesRecyclerViewItem.Title)
            is FixturesRecyclerViewHolder.FixturesViewHolder ->
                holder.bind(items[position] as FixturesRecyclerViewItem.Fixture)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is FixturesRecyclerViewItem.Title -> R.layout.item_title
            is FixturesRecyclerViewItem.Fixture -> R.layout.item_fixture
        }
    }
}