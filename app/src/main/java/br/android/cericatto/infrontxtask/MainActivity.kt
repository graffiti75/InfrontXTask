package br.android.cericatto.infrontxtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.android.cericatto.infrontxtask.adapter.ViewPagerAdapter
import br.android.cericatto.infrontxtask.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val titlesArray = arrayOf(
        "Fixtures",
        "Results"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
    }

    private fun setViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        tabLayout.setSelectedTabIndicatorHeight(10)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()
    }
}