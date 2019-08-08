package alytvyniuk.com.data.repository

import alytvyniuk.com.model.*
import io.reactivex.Single

internal class LaunchesRetrofitClient(private val launchesRetrofitApi: LaunchesRetrofitApi) : LaunchesRepository {

    override fun getLaunchesInRange(start: Int, count: Int): Single<LaunchesResponse> {
        return launchesRetrofitApi.getLaunches(start, count)
            .map { response ->
                if (response.isSuccessful) {
                    val l = response.body()?.map {
                        LaunchData(
                            it.flight_number,
                            it.mission_name,
                            it.rocket.rocket_name,
                            it.launch_date_unix * 1000L,
                            it.links.mission_patch_small,
                            it.launch_success,
                            it.upcoming,
                            it.details,
                            it.links.flickr_images
                        )
                    } ?: emptyList()
                    SuccessResponse(l)
                } else {
                    FailureResponse(Throwable(response.message()))
                }
            }
    }
}