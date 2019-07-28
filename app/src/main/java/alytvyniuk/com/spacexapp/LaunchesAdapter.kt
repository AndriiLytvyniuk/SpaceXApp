package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_launch.view.*
import java.lang.IllegalArgumentException

private const val TYPE_LAUNCH_ITEM = 0
private const val TYPE_PROGRESS = 1

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

        fun bind(item: LaunchesDataItem) {
            itemView.launchName.text = item.launchData.flight_number.toString()
        }
    }

    private inner class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}