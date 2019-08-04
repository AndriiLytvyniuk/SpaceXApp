package alytvyniuk.com.spacexapp.launchdetails

import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.inflate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_launch_details.*

class LaunchDetailsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        container?.inflate(R.layout.fragment_launch_details)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagesViewPager.adapter = LaunchImagePagerAdapter(childFragmentManager)
    }
}