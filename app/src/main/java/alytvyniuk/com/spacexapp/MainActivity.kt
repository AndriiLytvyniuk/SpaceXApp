package alytvyniuk.com.spacexapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val fragmentsNumber = 2
        viewPager.adapter = TabsAdapter(supportFragmentManager, fragmentsNumber, this)
        tabLayout.setupWithViewPager(viewPager)
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
