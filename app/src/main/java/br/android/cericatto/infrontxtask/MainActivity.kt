package br.android.cericatto.infrontxtask

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import br.android.cericatto.infrontxtask.adapter.ViewPagerAdapter
import br.android.cericatto.infrontxtask.databinding.ActivityMainBinding
import br.android.cericatto.infrontxtask.fragment.FixturesFragment
import br.android.cericatto.infrontxtask.util.isNetworkConnected
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
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
            initToolbar()
            setTabLayout()
        } else {
            Snackbar.make(binding.root, getString(R.string.network_down), 10000)
                .setAction(getString(R.string.here)) {
                    checkNetwork()
                }.show()
        }
    }

    private fun initToolbar() {
        binding.toolbar.visibility = View.VISIBLE
        setSupportActionBar(binding.toolbar)
        binding.toolbar.background = ContextCompat.getDrawable(this, R.color.grey_500)
    }

    private fun setTabLayout() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_500))
        tabLayout.setSelectedTabIndicatorHeight(10)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text == titlesArray[0]) {
                    val fragment = supportFragmentManager.fragments[0] as FixturesFragment
                    fragment.resetAdapter()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = setSearchView(searchItem, searchManager)
        searchViewListener(searchView, searchItem)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView {
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        val txtSearch = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = resources.getString(R.string.search_hint)
//        txtSearch.setHintTextColor(ContextCompat.getColor(this, R.color.blue_800))
//        txtSearch.setTextColor(ContextCompat.getColor(this, R.color.blue_1000))

        return searchView
    }

    private fun searchViewListener(searchView: SearchView?, searchItem: MenuItem) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val fragment = supportFragmentManager.fragments[0] as FixturesFragment
                fragment.performToolbarSearch(query)
                if (!searchView.isIconified) {
                    searchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }
}