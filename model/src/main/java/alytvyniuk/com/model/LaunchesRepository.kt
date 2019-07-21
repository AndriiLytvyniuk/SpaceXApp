package alytvyniuk.com.model

import io.reactivex.Single

interface LaunchesRepository {

    fun getAllLaunches() : Single<Result<List<LaunchData>>>

    fun getLaunchesInRange(start: Int, count: Int) : Single<Result<List<LaunchData>>>
}