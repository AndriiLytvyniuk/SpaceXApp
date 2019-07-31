package alytvyniuk.com.spacexapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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