package alytvyniuk.com.spacexapp.launchlist

import alytvyniuk.com.spacexapp.LaunchesDataItem
import alytvyniuk.com.spacexapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_launch.view.*
import java.lang.IllegalArgumentException

private const val TYPE_LAUNCH_ITEM = 0
private const val TYPE_PROGRESS = 1

class LaunchesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<LaunchesDataItem>()

    var allItemsReceived = false

    fun insertItems(newItems: List<LaunchesDataItem>) {
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

    override fun getItemCount() = if (allItemsReceived) items.size else items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LaunchesViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!allItemsReceived && position == items.size) {
            TYPE_PROGRESS
        } else {
            TYPE_LAUNCH_ITEM
        }
    }

    private inner class LaunchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: LaunchesDataItem) {
            itemView.launchName.text = item.launchData.flight_number.toString()
        }
    }

    private inner class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}