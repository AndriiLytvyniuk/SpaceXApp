package alytvyniuk.com.model

import io.reactivex.Single

interface LaunchesRepository {

    fun getLaunchesInRange(start: Int, count: Int) : Single<LaunchesResponse>
}