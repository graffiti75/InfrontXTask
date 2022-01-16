package br.android.cericatto.infrontxtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.android.cericatto.infrontxtask.adapter.ViewPagerAdapter
import br.android.cericatto.infrontxtask.databinding.ActivityMainBinding
import br.android.cericatto.infrontxtask.util.isNetworkConnected
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val titlesArray = arrayOf("Fixtures", "Results")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNetwork()
    }

    private fun checkNetwork() {
        if (isNetworkConnected()) {
            setViewPager()
        } else {
            Snackbar.make(binding.root, "Network down.\nClick here to try to connect again.", 10000)
                .setAction("Here") {
                    checkNetwork()
                }.show()
        }
    }

    private fun setViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_500))
        tabLayout.setSelectedTabIndicatorHeight(10)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()
    }
}