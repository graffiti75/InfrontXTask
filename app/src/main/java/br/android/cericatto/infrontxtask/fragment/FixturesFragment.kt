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
import br.android.cericatto.infrontxtask.adapter.FixturesAdapter
import br.android.cericatto.infrontxtask.data.fixture.FixtureItem
import br.android.cericatto.infrontxtask.databinding.FragmentFixturesBinding
import br.android.cericatto.infrontxtask.viewmodel.FixtureViewModel
import kotlinx.coroutines.flow.collect

class FixturesFragment : Fragment() {
    private lateinit var binding: FragmentFixturesBinding

    private lateinit var viewModel: FixtureViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[FixtureViewModel::class.java]

        viewModel.getFixtures()

        lifecycleScope.launchWhenStarted {
            viewModel.data.collect { event ->
                when(event) {
                    is FixtureViewModel.UIEvent.Success -> {
                        binding.progressBar.isVisible = false
                        setAdapter(event.items)
                    }
                    is FixtureViewModel.UIEvent.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is FixtureViewModel.UIEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setAdapter(items: List<FixtureItem>) {
        val itemAdapter = FixturesAdapter()
        binding.recyclerView.apply {
            adapter = itemAdapter
            val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this.context, R.drawable.decoration_divider)!!)
            addItemDecoration(dividerItemDecoration)
        }
        itemAdapter.submitList(items)
    }
}