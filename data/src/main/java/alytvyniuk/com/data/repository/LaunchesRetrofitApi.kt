package alytvyniuk.com.data.repository

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface LaunchesRetrofitApi {

    @GET("v3/launches")
    fun getLaunches(@Query("offset") from: Int, @Query("limit") number: Int): Single<Response<List<LaunchesResponseModel>>>

    @GET("v3/launches")
    fun getAllLaunches(): Single<Response<List<LaunchesResponseModel>>>
}