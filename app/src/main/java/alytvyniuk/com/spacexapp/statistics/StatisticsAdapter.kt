package alytvyniuk.com.spacexapp.statistics

import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.inflate
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_statistics_graph.view.*
import java.text.DateFormatSymbols

private const val TYPE_STATISTICS_ITEM = 0
private const val TYPE_EMPTY = 1
private const val TYPE_PROGRESS = 2
private val dateFormatSymbols = DateFormatSymbols()

class StatisticsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<StatisticsItem>()
    private var maxLaunches = 0

    var allItemsReceived = false

    fun insertItems(newItems: List<StatisticsItem>) {
        items = newItems
        maxLaunches = items.maxBy {it.launchesNumber}?.launchesNumber ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_STATISTICS_ITEM ->
                StatisticsViewHolder(parent.inflate(R.layout.list_item_statistics_graph), parent.measuredHeight)
            TYPE_PROGRESS ->
                StatisticsProgressViewHolder(parent.inflate(R.layout.list_item_statistics_loading))
            TYPE_EMPTY ->
                StatisticsEmptyViewHolder(parent.inflate(R.layout.list_item_statistics_empty))
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
        } else if (items[position].launchesNumber > 0) {
            TYPE_STATISTICS_ITEM
        } else {
            TYPE_EMPTY
        }
    }

    private inner class StatisticsViewHolder(itemView: View, private val parentHeight: Int): RecyclerView.ViewHolder(itemView) {

        init{
            itemView.setOnClickListener {
                itemView.launchesNumberText.visibility = View.VISIBLE
            }
        }

        fun bind(item: StatisticsItem) {
            itemView.dateText.text = "${dateFormatSymbols.shortMonths[item.month - 1]} ${item.year}"
            itemView.launchesNumberText.text = item.launchesNumber.toString()
            itemView.launchesNumberText.visibility = View.INVISIBLE
            val height = parentHeight.toDouble() * 0.7 * item.launchesNumber / maxLaunches
            itemView.graphView.layoutParams.height = height.toInt()
            itemView.graphView.invalidate()
        }
    }

    private class StatisticsProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private class StatisticsEmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

data class StatisticsItem(
    val launchesNumber: Int,
    val year: Int,
    val month: Int)