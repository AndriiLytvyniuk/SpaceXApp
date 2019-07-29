package alytvyniuk.com.spacexapp.launchlist

import alytvyniuk.com.spacexapp.App
import alytvyniuk.com.spacexapp.LaunchesModelFactory
import alytvyniuk.com.spacexapp.LaunchesViewModel
import alytvyniuk.com.spacexapp.R
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_launches.*
import javax.inject.Inject

class LaunchesFragment: Fragment() {

    @Inject
    lateinit var launchesModelFactory: LaunchesModelFactory

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("Andrii", "onCreateView")
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

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
        val adapter = LaunchesAdapter()

        launchesRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            this.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.recycler_separator_height
                    )
                )
            )
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
            launchesRecyclerView.post {
                adapter.insertItems(launches)
                adapter.notifyDataSetChanged()
            }

        })
        if (viewModel.launches.isEmpty()) {
            viewModel.requestMoreLaunches()
        }
    }

    private class SpaceItemDecoration(private val spaceSize: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = spaceSize
            outRect.right = spaceSize
        }
    }
}