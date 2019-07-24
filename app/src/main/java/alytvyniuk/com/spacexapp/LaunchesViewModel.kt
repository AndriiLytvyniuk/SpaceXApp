package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    @VisibleForTesting
    val launchesLiveData: MutableLiveData<LaunchesResponse> = MutableLiveData()

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<LaunchesResponse>) {
        launchesLiveData.observe(owner, observer)
    }

    fun requestLaunches(start: Int, count: Int) {
        val disposable = launchesRepository.getLaunchesInRange(start, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.isSuccess) {
                    launchesLiveData.value = LaunchesResponse(result.getOrThrow())
                }

            }, { t ->
                launchesLiveData.value = LaunchesResponse(exception = t)
            })
    }
}

class LaunchesModelFactory(private val launchesRepository: LaunchesRepository) : ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LaunchesViewModel(launchesRepository) as T
    }
}

data class LaunchesResponse(val barcode: List<LaunchData>? = null, val exception: Throwable? = null)
