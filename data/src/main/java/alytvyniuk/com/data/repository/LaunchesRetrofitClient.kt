package alytvyniuk.com.data.repository

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import io.reactivex.Single

internal class LaunchesRetrofitClient(private val launchesRetrofitApi: LaunchesRetrofitApi) : LaunchesRepository {
    override fun getLaunchesInRange(start: Int, count: Int): Single<Result<List<LaunchData>>> {
        return launchesRetrofitApi.getLaunches(start, count)
            .map { t ->
                if (t.isSuccessful) {
                    val l = t.body()?.map { responseModel ->
                        LaunchData(
                            responseModel.flight_number,
                            responseModel.launch_date_unix)
                    } ?: emptyList()
                    Result.success(l)
                } else {
                    Result.failure(Throwable(t.message()))
                }
            }
    }
}