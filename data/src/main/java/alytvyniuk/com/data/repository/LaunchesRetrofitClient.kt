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
                        LaunchData(responseModel.flight_number)
                    } ?: emptyList()
                    Result.success(l)
                } else {
                    Result.failure(Throwable(t.message()))
                }
            }
    }

    override fun getAllLaunches(): Single<Result<List<LaunchData>>> {
        return launchesRetrofitApi.getAllLaunches()
            .map { t ->
                if (t.isSuccessful) {
                    val l = t.body()?.map { responseModel ->
                        LaunchData(responseModel.flight_number)
                    } ?: emptyList()
                    Result.success(l)
                } else {
                    Result.failure<List<LaunchData>>(Throwable(t.message()))
                }
            }
    }
}