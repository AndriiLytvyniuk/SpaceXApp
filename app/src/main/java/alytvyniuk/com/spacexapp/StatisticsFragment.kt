package alytvyniuk.com.spacexapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.util.*
import javax.inject.Inject

class StatisticsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("Andrii", "onCreateView StatisticsFragment")

        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    @Inject
    lateinit var launchesModelFactory: LaunchesModelFactory

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component().inject(this)
        viewModel = ViewModelProviders.of(
            this,
            launchesModelFactory
        ).get(LaunchesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Andrii", "onViewCreated")
        val context = view.context
        val adapter = StatisticsAdapter()

        statisticsRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.layoutManager = layoutManager
            this.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation).apply {
                setDrawable(context.getDrawable(R.drawable.list_separator_decoration)!!)
            })
            this.adapter = adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    //Log.d("Andrii", "onScrollStateChanged ${layoutManager.findLastCompletelyVisibleItemPosition()}")
                    if (lastPosition > viewModel.launches.size - 3) {
                        viewModel.requestMoreLaunches()
                    }
                }
            })
        }

        viewModel.observe(this, Observer { launches ->



        })
        if (viewModel.launches.isEmpty()) {
            viewModel.requestMoreLaunches()
        }
    }

    private fun getLaunchesPerMonth(launchesListItems: List<LaunchesListItem>) {
        val launchesPerMonth = launchesListItems
            .asSequence()
            .takeWhile { it is LaunchesDataItem }
            .groupingBy {
                val date = ((it as LaunchesDataItem).launchData.launchDate).toLong() * 1000
                val c = Calendar.getInstance()
                c.time = Date(date)
                c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH)
            }
            .eachCount()

        val min = launchesPerMonth.minBy { it.key } ?: return
        val startYear = min.key / 100
        val startMonth = min.key % 100

        val firstItem = StatisticsItem()

        val statisticsList = mutableListOf<StatisticsItem>()
        statisticsList.add(launchesPerMonth.g)

    }
}