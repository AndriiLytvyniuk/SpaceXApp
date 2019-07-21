package alytvyniuk.com.data.repository

import alytvyniuk.com.model.LaunchData
import alytvyniuk.com.model.LaunchesRepository
import io.reactivex.Single

internal class LaunchesRetrofitClient(val launchesRetrofitApi: LaunchesRetrofitApi): LaunchesRepository {
    override fun getAllLaunches(): Single<Result<List<LaunchData>>> {
            return Single.fromCallable {
                launchesRetrofitApi.getAllLaunches().map {
                    if(it.isSuccessful) {
                        val l = it.body()?.map { responseModel ->
                            LaunchData(responseModel.flight_number)
                        }
                        Result.success(l)
                    }
                }
            }
    }

    override fun getLaunchesInRange(start: Int, count: Int): Single<Result<List<LaunchData>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}