package alytvyniuk.com.spacexapp.launchlist

import alytvyniuk.com.spacexapp.App
import alytvyniuk.com.spacexapp.LaunchesViewModel
import alytvyniuk.com.spacexapp.R
import alytvyniuk.com.spacexapp.utils.inflate
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_launches.*

class LaunchesFragment: Fragment() {

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        container?.inflate(R.layout.fragment_launches)

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
        val adapter = LaunchesAdapter(App.component.imageLoader())

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
                    if (lastPosition > (viewModel.launchesLiveData.value?.size ?: 0) - 3) {
                        viewModel.requestMoreLaunches()
                    }
                }
            })
        }

        viewModel.launchesLiveData.observe(this, Observer { launches ->
            launchesRecyclerView.post {
                adapter.insertItems(launches)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private class SpaceItemDecoration(private val spaceSize: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = spaceSize
            outRect.right = spaceSize
        }
    }
}