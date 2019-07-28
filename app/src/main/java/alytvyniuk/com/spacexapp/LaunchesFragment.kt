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
import java.util.*
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
            launchesRecyclerView.post {
                adapter.insertItems(launches)
                adapter.notifyDataSetChanged()
            }

        })
        if (viewModel.launches.isEmpty()) {
            viewModel.requestMoreLaunches()
        }
    }

}