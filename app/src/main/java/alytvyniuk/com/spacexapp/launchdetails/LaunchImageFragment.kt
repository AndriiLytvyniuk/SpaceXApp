package alytvyniuk.com.spacexapp.launchdetails

import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.inflate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

class LaunchImageFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        container?.inflate(R.layout.fragment_launch_image)

    fun setImageUrl(imageUrl: String) : Fragment {
        arguments = Bundle().apply {
            putString(KEY_IMAGE_URL, imageUrl)
        }
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}