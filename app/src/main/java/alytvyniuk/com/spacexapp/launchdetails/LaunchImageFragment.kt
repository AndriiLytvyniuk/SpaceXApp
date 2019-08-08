package alytvyniuk.com.spacexapp.launchdetails

import alytvyniuk.com.spacexapp.App
import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.utils.inflate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_launch_image.*
import android.app.DownloadManager
import android.net.Uri
import androidx.core.content.ContextCompat.getSystemService
import android.os.Environment


private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"
private const val DOWNLOAD_BUTTON_SHOW_TIME = 2000L

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
        val imageUrl = arguments?.getString(KEY_IMAGE_URL)
        if (imageUrl != null) {
            App.component.imageLoader().loadImage(imageUrl, launchImage)
            downloadButton.setOnClickListener {
                downloadImage(imageUrl)
            }
            launchImage.setOnClickListener {
                downloadButton.show()
                downloadButton.postDelayed({downloadButton.hide()}, DOWNLOAD_BUTTON_SHOW_TIME)
            }
        }
    }

    private fun downloadImage(imageUrl: String) {
        val fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1, imageUrl.length)
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        getSystemService(requireContext(), DownloadManager::class.java)?.enqueue(request)
    }
}