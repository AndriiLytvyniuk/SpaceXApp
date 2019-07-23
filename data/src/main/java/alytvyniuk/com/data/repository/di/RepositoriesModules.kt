package alytvyniuk.com.data.repository.di

import alytvyniuk.com.data.repository.LaunchesRetrofitApi
import alytvyniuk.com.data.repository.LaunchesRetrofitClient
import alytvyniuk.com.model.LaunchesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideCodeToImageConverter() : LaunchesRepository {
        return LaunchesRetrofitClient(
            Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://en.wikipedia.org/w/")
                .build()
                .create(LaunchesRetrofitApi::class.java)
        )
    }
}
