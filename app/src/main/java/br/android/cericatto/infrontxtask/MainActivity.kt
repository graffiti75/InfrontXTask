package br.android.cericatto.infrontxtask

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import br.android.cericatto.infrontxtask.adapter.ResultsAdapter
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.databinding.ActivityMainBinding
import br.android.cericatto.infrontxtask.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getData()

        lifecycleScope.launchWhenStarted {
            viewModel.data.collect { event ->
                when(event) {
                    is MainViewModel.UIEvent.Success -> {
                        binding.progressBar.isVisible = false
                        setAdapter(event.items)

                    }
                    is MainViewModel.UIEvent.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is MainViewModel.UIEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

//    private fun setAdapter(items: List<FixtureItem>) {
    private fun setAdapter(items: List<ResultItem>) {
//        val itemAdapter = FixturesAdapter()
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