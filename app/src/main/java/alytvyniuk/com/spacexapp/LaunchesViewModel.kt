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

class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    @VisibleForTesting
    val liveData: MutableLiveData<LaunchesResponse> = MutableLiveData()

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<LaunchesResponse>) {
        liveData.observe(owner, observer)
    }
}

class LaunchesModelFactory(private val launchesRepository: LaunchesRepository) : ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LaunchesViewModel(launchesRepository) as T
    }
}

data class LaunchesResponse(val barcode: List<LaunchData>? = null, val exception: Throwable? = null)
