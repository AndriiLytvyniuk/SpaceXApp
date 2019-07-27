package alytvyniuk.com.spacexapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class StatisticsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("Andrii", "onCreateView StatisticsFragment")

        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("Andrii", "onAttach StatisticsFragment")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Andrii", "onActivityCreated StatisticsFragment")
    }
}