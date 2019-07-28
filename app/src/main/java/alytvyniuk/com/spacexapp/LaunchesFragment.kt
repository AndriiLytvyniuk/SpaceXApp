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
import kotlinx.android.synthetic.main.fragment_launches.*
import javax.inject.Inject

private const val REQUEST_THRESHOLD = 10

class LaunchesFragment: Fragment() {

    @Inject
    lateinit var launchesModelFactory: LaunchesModelFactory

    private lateinit var viewModel: LaunchesViewModel

    init {
        App.component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val adapter = LaunchesAdapter()
        viewModel = ViewModelProviders.of(
            this,
            launchesModelFactory
        ).get(LaunchesViewModel::class.java)
        launchesRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context)
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
                        requestMoreItems()
                    }
                }
            })
        }

        viewModel.observe(this, Observer { launches ->
            adapter.insertItems(launches)
            adapter.notifyDataSetChanged()
        })
        if (viewModel.launches.isEmpty()) {
            requestMoreItems()
        }
    }

    private fun requestMoreItems() {
        val start = viewModel.launches.size
        viewModel.requestLaunches(start, REQUEST_THRESHOLD)
    }


}