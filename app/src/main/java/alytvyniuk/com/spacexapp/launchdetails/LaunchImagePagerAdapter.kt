package alytvyniuk.com.spacexapp.launchdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class LaunchImagePagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return LaunchImageFragment()
    }

    override fun getCount(): Int {
        return 5
    }

}