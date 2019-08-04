package alytvyniuk.com.spacexapp.launchdetails

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.spacexapp.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_launch_details.*
import java.text.SimpleDateFormat
import java.util.*

private const val KEY_LAUNCH_INDEX = "KEY_LAUNCH_INDEX"
private val DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy", Locale.US)

class LaunchDetailsFragment: Fragment() {

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            requireActivity(),
            App.component().launchesModelFactory()
        ).get(LaunchesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        container?.inflate(R.layout.fragment_launch_details)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val launchIndex = arguments?.getInt(KEY_LAUNCH_INDEX)
        if (launchIndex != null) {
            val launchListItem = viewModel.launchesLiveData.value?.get(launchIndex)
            if (launchListItem != null && launchListItem is LaunchesDataItem) {
                initUi(launchListItem.launchData)
            }
        }
    }

    private fun initUi(data: LaunchData) {
        missionName.text = data.missionName
        missionDate.text = DATE_FORMAT.format(Date(data.missionDate))
        rocketName.text = data.rocketName
        missionDetails.text = data.details
        data.images.let {
            if (it.isEmpty()) {
                imagesViewPager.visibility = View.GONE
            } else {
                imagesViewPager.adapter = LaunchImagePagerAdapter(childFragmentManager, it)
            }
        }
    }

    fun setArguments(launchIndex: Int) {
        arguments = Bundle().apply {
            putInt(KEY_LAUNCH_INDEX, launchIndex)
        }
    }
}