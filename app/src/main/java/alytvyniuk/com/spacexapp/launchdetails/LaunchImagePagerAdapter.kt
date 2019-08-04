package alytvyniuk.com.spacexapp.launchdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class LaunchImagePagerAdapter(
    fragmentManager: FragmentManager,
    private val images: List<String>
): FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return LaunchImageFragment().setImageUrl(images[position])
    }

    override fun getCount(): Int {
        return images.size
    }

}