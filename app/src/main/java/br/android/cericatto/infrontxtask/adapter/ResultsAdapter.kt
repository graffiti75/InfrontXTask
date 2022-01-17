package br.android.cericatto.infrontxtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.databinding.ItemResultBinding
import br.android.cericatto.infrontxtask.databinding.ItemTitleBinding

class ResultsAdapter : RecyclerView.Adapter<ResultsRecyclerViewHolder>() {

    var items = listOf<ResultsRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsRecyclerViewHolder {
        return when(viewType){
            R.layout.item_title -> ResultsRecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_result -> ResultsRecyclerViewHolder.ResultsViewHolder(
                ItemResultBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: ResultsRecyclerViewHolder, position: Int) {
        when(holder){
            is ResultsRecyclerViewHolder.TitleViewHolder ->
                holder.bind(items[position] as ResultsRecyclerViewItem.Title)
            is ResultsRecyclerViewHolder.ResultsViewHolder ->
                holder.bind(items[position] as ResultsRecyclerViewItem.Result)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is ResultsRecyclerViewItem.Title -> R.layout.item_title
            is ResultsRecyclerViewItem.Result -> R.layout.item_result
        }
    }
}