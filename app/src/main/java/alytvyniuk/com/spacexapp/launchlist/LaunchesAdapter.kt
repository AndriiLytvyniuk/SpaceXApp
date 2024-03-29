package alytvyniuk.com.spacexapp.launchlist

import alytvyniuk.com.model.ImageLoader
import alytvyniuk.com.spacexapp.*
import alytvyniuk.com.spacexapp.launchdetails.LaunchDetailsFragment
import alytvyniuk.com.spacexapp.utils.getLaunchStatusColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_launch.view.*
import java.text.SimpleDateFormat
import java.util.*

private const val TYPE_LAUNCH_ITEM = 0
private const val TYPE_PROGRESS = 1
private val DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy", Locale.US)

class LaunchesAdapter(private val imageLoader: ImageLoader): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<LaunchesItem>()

    fun insertItems(newItems: List<LaunchesItem>) {
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
            val context = itemView.context
            item.launchData.apply {
                itemView.missionName.text = missionName
                itemView.rocketName.text = context.getString(R.string.rocket_name, rocketName)
                itemView.missionDate.text = context.getString(
                    R.string.launch_date,
                    DATE_FORMAT.format(Date(missionDate))
                )
                itemView.colorFrame.setBackgroundColor(this.getLaunchStatusColor(context))
                missionImage.let {
                    if (it != null) {
                        imageLoader.loadImage(it, itemView.missionImage, ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
                    }
                }

            }
        }

    }


    private inner class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}