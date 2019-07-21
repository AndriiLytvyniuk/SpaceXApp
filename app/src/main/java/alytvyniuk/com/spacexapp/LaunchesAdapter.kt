package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

private const val TYPE_LAUNCH_ITEM = 0
private const val TYPE_PROGRESS = 1

class LaunchesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<LaunchesListItem>()

    fun insertItems(start: Int, newItems: Collection<LaunchesListItem>) {
        if (start > items.size) {
            throw IllegalArgumentException("Start position is bigger than possible")
        }
        val numberToReplace = items.size - start
        var counter = 0
        newItems.forEach {newItem ->
            if (counter < numberToReplace) {
                items[start + counter] = newItem
            } else {
                items.add(newItem)
            }
            counter++
        }
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

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is LaunchesDataItem -> TYPE_LAUNCH_ITEM
            is ProgressItem -> TYPE_PROGRESS
        }
    }
}

private class LaunchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

private class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

sealed class LaunchesListItem
data class LaunchesDataItem(val launchData: LaunchData) : LaunchesListItem()
object ProgressItem : LaunchesListItem()