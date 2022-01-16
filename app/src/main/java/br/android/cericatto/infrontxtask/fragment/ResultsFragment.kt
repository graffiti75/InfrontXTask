package br.android.cericatto.infrontxtask.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.adapter.ResultsAdapter
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.databinding.FragmentResultsBinding
import br.android.cericatto.infrontxtask.viewmodel.ResultsViewModel
import kotlinx.coroutines.flow.collect

class ResultsFragment : Fragment() {
    private lateinit var binding: FragmentResultsBinding

    private lateinit var viewModel: ResultsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ResultsViewModel::class.java]

        viewModel.getResults()

        lifecycleScope.launchWhenStarted {
            viewModel.data.collect { event ->
                when(event) {
                    is ResultsViewModel.UIEvent.Success -> {
                        binding.progressBar.isVisible = false
                        setAdapter(event.items)
                    }
                    is ResultsViewModel.UIEvent.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is ResultsViewModel.UIEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setAdapter(items: List<ResultItem>) {
        val itemAdapter = ResultsAdapter()
        binding.recyclerView.apply {
            adapter = itemAdapter
            val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this.context, R.drawable.decoration_divider)!!)
            addItemDecoration(dividerItemDecoration)
        }
        itemAdapter.submitList(items)
    }
}