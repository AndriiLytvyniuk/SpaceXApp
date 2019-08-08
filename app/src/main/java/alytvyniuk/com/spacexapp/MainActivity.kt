package alytvyniuk.com.spacexapp

import alytvyniuk.com.spacexapp.launchlist.LaunchesFragment
import alytvyniuk.com.spacexapp.statistics.StatisticsFragment
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val fragmentsNumber = 2
        viewPager.adapter = TabsAdapter(supportFragmentManager, fragmentsNumber, this)
        tabLayout.setupWithViewPager(viewPager)
        val viewModel = ViewModelProviders.of(
            this,
            App.component.launchesModelFactory()
        ).get(LaunchesViewModel::class.java)
        if (viewModel.launchesLiveData.value.isNullOrEmpty()) {
            viewModel.requestMoreLaunches()
        }
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show()
        })
    }

    inner class TabsAdapter(
        fm: FragmentManager,
        private val tabsNumber: Int,
        private val context: Context
    ) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return tabsNumber
        }

        override fun getItem(position: Int) = when (position) {
            0 -> LaunchesFragment()
            1 -> StatisticsFragment()
            else -> throw IllegalArgumentException("Unknown tab")
        }

        override fun getPageTitle(position: Int): String = when (position) {
            0 -> context.getString(R.string.launches)
            1 -> context.getString(R.string.statistics)
            else -> throw IllegalArgumentException("Unknown tab")
        }
    }
}
