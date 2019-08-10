package alytvyniuk.com.spacexapp.statistics

import alytvyniuk.com.spacexapp.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.util.*

class StatisticsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            requireActivity(),
            App.component.launchesModelFactory()
        ).get(LaunchesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val adapter = StatisticsAdapter()

        statisticsRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.layoutManager = layoutManager
            this.adapter = adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    if (lastPosition > (recyclerView.adapter?.itemCount ?: 0) - 5) {
                        viewModel.requestMoreLaunches()
                    }
                }
            })
        }

        viewModel.launchesLiveData.observe(this, Observer { launches ->
            statisticsRecyclerView.post {
                val items = getLaunchesPerMonth(launches)
                adapter.insertItems(items)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.allItemsReceived.observe(this, Observer {
            adapter.allItemsReceived = it
        })
    }

    private fun getLaunchesPerMonth(launchesItems: List<LaunchesItem>): List<StatisticsItem> {
        val launchesPerMonth = launchesItems
            .asSequence()
            .takeWhile { it is LaunchesDataItem }
            .groupingBy {
                val date = (it as LaunchesDataItem).launchData.missionDate
                val c = Calendar.getInstance()
                c.time = Date(date)
                c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1
            }
            .eachCount()
            .toMutableMap()

        val statisticsList = mutableListOf<StatisticsItem>()

        val min = launchesPerMonth.minBy { it.key } ?: return statisticsList
        var iterator = min.key
        while(launchesPerMonth.isNotEmpty()) {
            val launchesNumber = launchesPerMonth.remove(iterator) ?: 0
            val month = iterator % 100
            statisticsList.add(
                StatisticsItem(
                    launchesNumber,
                    iterator / 100,
                    month
                )
            )
            if (month >= 12) {
                iterator = (iterator / 100 + 1) * 100 + 1
            } else {
                iterator++
            }
        }
        return statisticsList
    }
}