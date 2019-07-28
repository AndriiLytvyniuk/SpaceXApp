package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val REQUEST_THRESHOLD = 10

class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    private val launchesLiveData = MutableLiveData<MutableList<LaunchesListItem>>().apply { this.value = mutableListOf() }

    var launches : List<LaunchesListItem> = emptyList()
    get() = launchesLiveData.value ?: emptyList()

    var allItemsReceived = false

    private val disposables = CompositeDisposable()

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<MutableList<LaunchesListItem>>) {
        launchesLiveData.observe(owner, observer)
    }

    fun requestMoreLaunches() {
        if (allItemsReceived) {
            return
        }
        val items = launchesLiveData.value ?: mutableListOf()
        val start = launches.size
        val count = REQUEST_THRESHOLD
        items.insertFromPosition(start, List(count) { ProgressItem })
        launchesLiveData.value = items

        Log.d("Andrii", "requestMoreLaunches from $start to ${start + count}")

        disposables.add(launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.isSuccess) {
                    val oldItems = launchesLiveData.value ?: mutableListOf()
                    val newItems = result.getOrDefault(emptyList())
                    Log.d("Andrii", "received from $start to ${start + count} size ${newItems.size}" )
                    oldItems.insertFromPosition(
                        start,
                        result.getOrNull()?.map { LaunchesDataItem(it) } ?: listOf()
                    )
                    if (newItems.size < count) {
                        allItemsReceived = true
                        val before = oldItems.size
                        repeat(count - newItems.size) {
                            oldItems.removeAt(oldItems.size - 1)
                        }
                        Log.d("Andrii", "End reached $start to ${start + count} size ${newItems.size} before $before " )
                    }
                    launchesLiveData.value = oldItems
                }
            }, { t ->
                Log.e("Andrii", "exception", t)
            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}

class LaunchesModelFactory @Inject constructor(
    private val launchesRepository: LaunchesRepository
) : ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LaunchesViewModel(launchesRepository) as T
    }
}

sealed class LaunchesListItem
data class LaunchesDataItem(val launchData: LaunchData) : LaunchesListItem()
object ProgressItem : LaunchesListItem()
