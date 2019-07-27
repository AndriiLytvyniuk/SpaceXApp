package alytvyniuk.com.spacexapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_launches.*
import javax.inject.Inject

class LaunchesFragment: Fragment() {

    @Inject
    lateinit var launchesModelFactory: LaunchesModelFactory

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
        launchesRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            this.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation).apply {
                setDrawable(context.getDrawable(R.drawable.list_separator_decoration)!!)
            })
            this.adapter = adapter
        }

        val viewModel = ViewModelProviders.of(
            this,
            launchesModelFactory
        ).get(LaunchesViewModel::class.java)
        viewModel.observe(this, Observer { launches ->
            adapter.insertItems(launches)
            adapter.notifyDataSetChanged()
        })
        viewModel.requestLaunches(0, 7)
    }


}