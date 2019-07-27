package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.model.LaunchesRepository
import alytvyniuk.com.spacexapp.LaunchesModelFactory
import dagger.Module
import dagger.Provides

@Module
class LaunchesModelFactoryModel {

    @Provides
    fun provideLaunchesModelFactory(launchesRepository: LaunchesRepository) =
        LaunchesModelFactory(launchesRepository)
}