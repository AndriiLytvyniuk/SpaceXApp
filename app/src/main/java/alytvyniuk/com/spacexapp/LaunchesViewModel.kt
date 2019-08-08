package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import alytvyniuk.com.model.SuccessResponse
import android.util.Log
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val REQUEST_THRESHOLD = 10

class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {


    private val _launchesLiveData = MutableLiveData<MutableList<LaunchesListItem>>().apply { this.value = mutableListOf() }
    val launchesLiveData: LiveData<MutableList<LaunchesListItem>>
        get() = _launchesLiveData

    private val _allItemsReceived = MutableLiveData<Boolean>()
    val allItemsReceived: LiveData<Boolean>
    get() = _allItemsReceived

    private val disposables = CompositeDisposable()

    fun requestMoreLaunches() {
        if (allItemsReceived.value == true) {
            return
        }
        val items = launchesLiveData.value ?: mutableListOf()
        val start = items.size
        val count = REQUEST_THRESHOLD
        items.addAll(List(count) { ProgressItem })
        _launchesLiveData.value = items

        Log.d("Andrii", "requestMoreLaunches from $start to ${start + count}")

        disposables.add(launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result is SuccessResponse) {
                    val oldItems = _launchesLiveData.value ?: mutableListOf()
                    val newItems = result.launches
                    Log.d("Andrii", "received from $start to ${start + count} size ${newItems.size}" )
                    oldItems.insertFromPosition(
                        start,
                        result.launches.map { LaunchesDataItem(it) }
                    )
                    if (newItems.size < count) {
                        _allItemsReceived.value = true
                        val before = oldItems.size
                        repeat(count - newItems.size) {
                            oldItems.removeAt(oldItems.size - 1)
                        }
                        Log.d("Andrii", "End reached $start to ${start + count} size ${newItems.size} before $before " )
                    }
                    _launchesLiveData.value = oldItems
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
