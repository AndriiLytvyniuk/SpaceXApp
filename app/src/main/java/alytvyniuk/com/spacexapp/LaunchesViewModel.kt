package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.FailureResponse
import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import alytvyniuk.com.model.SuccessResponse
import alytvyniuk.com.spacexapp.utils.EventLiveData
import alytvyniuk.com.spacexapp.utils.insertFromPosition
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@VisibleForTesting
const val REQUEST_THRESHOLD = 10

class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    private val _launchesLiveData =
        MutableLiveData<MutableList<LaunchesItem>>()
    val launchesLiveData: LiveData<MutableList<LaunchesItem>>
        get() = _launchesLiveData

    private val _allItemsReceived = MutableLiveData<Boolean>()
    val allItemsReceived: LiveData<Boolean>
        get() = _allItemsReceived

    private val _errorLiveData = EventLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val disposables = CompositeDisposable()

    fun requestMoreLaunches() {
        if (allItemsReceived.value == true) {
            return
        }
        val items = launchesLiveData.value?.toMutableList() ?: mutableListOf()
        val start = items.size
        val count = REQUEST_THRESHOLD
        items.addAll(List(count) { ProgressItem })
        _launchesLiveData.value = items

        disposables.add(launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result is SuccessResponse) {
                    val oldItems = launchesLiveData.value?.toMutableList() ?: mutableListOf()
                    val newItems = result.launches
                    oldItems.insertFromPosition(
                        start,
                        result.launches.map { LaunchesDataItem(it) }
                    )
                    if (newItems.size < count) {
                        _allItemsReceived.value = true
                        repeat(count - newItems.size) {
                            oldItems.removeAt(oldItems.size - 1)
                        }
                    }
                    _launchesLiveData.value = oldItems
                } else if (result is FailureResponse) {
                    _errorLiveData.value = result.error
                }
            }, { error ->
                _errorLiveData.value = error
            })
        )
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

sealed class LaunchesItem
data class LaunchesDataItem(val launchData: LaunchData) : LaunchesItem()
object ProgressItem : LaunchesItem()
