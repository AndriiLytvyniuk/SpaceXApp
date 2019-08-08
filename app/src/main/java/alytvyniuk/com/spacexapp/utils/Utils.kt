package alytvyniuk.com.spacexapp.utils

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.spacexapp.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T> MutableList<T>.insertFromPosition(start: Int, listToMerge: List<T>) {
    if (listToMerge.isEmpty()) {
        return
    }
    if (start > size) {
        throw IllegalArgumentException("Start position is bigger than possible")
    }
    val numberToReplace = size - start
    listToMerge.forEachIndexed { i, item ->
        if (i < numberToReplace) {
            this[start + i] = item
        } else {
            add(item)
        }
    }
}

fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resource, this, attachToRoot)

fun LaunchData.getLaunchStatusColor(context: Context) =
    ContextCompat.getColor(
        context,
        when {
            isUpcoming -> R.color.upcoming_launch
            isSuccess == true -> R.color.successful_launch
            else -> R.color.failed_launch
        }
    )

inline fun <reified T : ViewModel> Fragment.viewModelProvider(
    crossinline factoryProvider: () -> ViewModelProvider.Factory
): Lazy<T> = lazy { ViewModelProviders.of(this, factoryProvider()).get(T::class.java) }

inline fun <reified R> createViewModelFactory(
    crossinline viewModelProvider: () -> ViewModel
) = object : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(R::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return viewModelProvider() as T
    }
}