package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

fun <T> MutableList<T>.insertFromPosition(start: Int, listToMerge: List<T>) {
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