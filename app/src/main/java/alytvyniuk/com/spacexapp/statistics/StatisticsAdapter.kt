package alytvyniuk.com.spacexapp.statistics

import alytvyniuk.com.spacexapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_statistics_graph.view.*
import java.text.DateFormatSymbols

private const val TYPE_STATISTICS_ITEM = 0
private const val TYPE_PROGRESS = 1
private val dateFormatSymbols = DateFormatSymbols()

class StatisticsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<StatisticsItem>()

    var allItemsReceived = false

    fun insertItems(newItems: List<StatisticsItem>) {
        items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_STATISTICS_ITEM ->
                StatisticsViewHolder(
                    inflater.inflate(R.layout.list_item_statistics_graph, parent, false)
                )
            TYPE_PROGRESS ->
                StatisticsProgressViewHolder(
                    inflater.inflate(R.layout.list_item_statistics_loading, parent, false)
                )
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    override fun getItemCount() = items.size + if (allItemsReceived) 0 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StatisticsViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!allItemsReceived && position == items.size) {
            TYPE_PROGRESS
        } else {
            TYPE_STATISTICS_ITEM
        }
    }

    private class StatisticsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: StatisticsItem) {
//            itemView.month.text = "${dateFormatSymbols.months[item.month - 1]}"
//            itemView.year.text = "${item.year}"
        }
    }

    private class StatisticsProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

data class StatisticsItem(
    val launchesNumber: Int,
    val year: Int,
    val month: Int)