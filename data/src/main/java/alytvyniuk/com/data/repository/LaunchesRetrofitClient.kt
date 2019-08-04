package alytvyniuk.com.data.repository

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import io.reactivex.Single

internal class LaunchesRetrofitClient(private val launchesRetrofitApi: LaunchesRetrofitApi) : LaunchesRepository {
    override fun getLaunchesInRange(start: Int, count: Int): Single<Result<List<LaunchData>>> {
        return launchesRetrofitApi.getLaunches(start, count)
            .map { t ->
                if (t.isSuccessful) {
                    val l = t.body()?.map {
                        LaunchData(
                            it.flight_number,
                            it.mission_name,
                            it.rocket.rocket_name,
                            it.launch_date_unix * 1000L,
                            it.links.mission_patch_small,
                            it.launch_success,
                            it.upcoming,
                            it.details)
                    } ?: emptyList()
                    Result.success(l)
                } else {
                    Result.failure(Throwable(t.message()))
                }
            }
    }
}