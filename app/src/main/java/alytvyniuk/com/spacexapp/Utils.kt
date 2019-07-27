package alytvyniuk.com.spacexapp

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