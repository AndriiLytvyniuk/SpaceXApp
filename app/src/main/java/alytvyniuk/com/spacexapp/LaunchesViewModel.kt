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

    private val launchesLiveData = MutableLiveData<MutableList<LaunchesDataItem>>().apply { this.value = mutableListOf() }

    private val allLaunchesReceivedLiveData = MutableLiveData<Boolean>()

    var launches : List<LaunchesDataItem> = emptyList()
    get() = launchesLiveData.value ?: emptyList()

    private val disposables = CompositeDisposable()

    fun observeLaunches(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<MutableList<LaunchesDataItem>>) {
        launchesLiveData.observe(owner, observer)
    }

    fun observeAllLaunchesReceived(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<Boolean>) {
        allLaunchesReceivedLiveData.observe(owner, observer)
    }

    fun requestMoreLaunches() {
        if (allLaunchesReceivedLiveData.value == true) {
            return
        }
        val items = launchesLiveData.value ?: mutableListOf()
        val start = items.size
        val count = REQUEST_THRESHOLD

        Log.d("Andrii", "requestMoreLaunches from $start to ${start + count}")

        disposables.add(launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.isSuccess) {
                    val oldItems = launchesLiveData.value ?: mutableListOf()
                    val newItems = result.getOrDefault(emptyList())
                    Log.d("Andrii", "received from $start to ${start + count} size ${newItems.size}" )
                    oldItems.addAll(result.getOrNull()?.map { LaunchesDataItem(it) } ?: listOf())
                    if (newItems.size < count) {
                        allLaunchesReceivedLiveData.value = true
                        Log.d("Andrii", "End reached $start to ${start + count} size ${newItems.size}" )
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

data class LaunchesDataItem(val launchData: LaunchData)
