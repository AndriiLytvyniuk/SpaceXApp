package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchesRepository
import androidx.annotation.NonNull
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    private val launchesLiveData = MutableLiveData<MutableList<LaunchesListItem>>().apply {
        this.value = mutableListOf()
    }
    private val disposables = CompositeDisposable()

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<MutableList<LaunchesListItem>>) {
        launchesLiveData.observe(owner, observer)
    }

    fun requestLaunches(start: Int, count: Int) {
        val items = launchesLiveData.value ?: mutableListOf()
        items.insertFromPosition(start, List(count) { ProgressItem })
        launchesLiveData.value = items

        disposables.add(launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.isSuccess) {
                    val oldItems = launchesLiveData.value!!
                    oldItems.insertFromPosition(
                        start,
                        result.getOrNull()?.map { LaunchesDataItem(it) } ?: listOf()
                    )
                    launchesLiveData.value = oldItems
                }
            }, { t ->
                //launchesLiveData.value = LaunchesResponse(exception = t)
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
