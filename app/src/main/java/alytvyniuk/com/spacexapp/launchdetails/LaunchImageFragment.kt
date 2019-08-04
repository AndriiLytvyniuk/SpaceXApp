package alytvyniuk.com.spacexapp.launchdetails

import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.inflate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class LaunchImageFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        container?.inflate(R.layout.fragment_launch_image)
}