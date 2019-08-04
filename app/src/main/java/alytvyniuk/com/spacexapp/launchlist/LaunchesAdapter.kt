package alytvyniuk.com.spacexapp.launchlist

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.spacexapp.LaunchesDataItem
import alytvyniuk.com.spacexapp.LaunchesListItem
import alytvyniuk.com.spacexapp.ProgressItem
import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.launchdetails.LaunchDetailsFragment
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_launch.view.*
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

private const val TYPE_LAUNCH_ITEM = 0
private const val TYPE_PROGRESS = 1
private val DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy", Locale.US)

class LaunchesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<LaunchesListItem>()

    fun insertItems(newItems: List<LaunchesListItem>) {
        items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_LAUNCH_ITEM ->
                LaunchesViewHolder(
                    inflater.inflate(R.layout.list_item_launch, parent, false))
            TYPE_PROGRESS ->
                ProgressViewHolder(
                    inflater.inflate(R.layout.list_item_loading, parent, false))
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LaunchesViewHolder -> {
                holder.bind(items[position] as LaunchesDataItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is LaunchesDataItem -> TYPE_LAUNCH_ITEM
            is ProgressItem -> TYPE_PROGRESS
        }
    }

    private inner class LaunchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                (it.context as FragmentActivity)
                    .supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.fragmentContainer,
                        LaunchDetailsFragment().apply { setArguments(adapterPosition) },
                        "launch_details")
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun bind(item: LaunchesDataItem) {
            item.launchData.apply {
                itemView.missionName.text = missionName
                itemView.rocketName.text = rocketName
                itemView.missionDate.text = DATE_FORMAT.format(Date(missionDate))
                itemView.launchesLayout.background = getCardBackground(this)
            }
        }

        private fun getCardBackground(launchData: LaunchData) = ColorDrawable(
            ContextCompat.getColor(
                itemView.context,
                when {
                    launchData.isUpcoming -> R.color.upcoming_launch
                    launchData.isSuccess == true -> R.color.successful_launch
                    else -> R.color.failed_launch
                }
            )
        )
    }


    private inner class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}