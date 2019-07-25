package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.model.LaunchesRepository
import alytvyniuk.com.spacexapp.LaunchesModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class LaunchesModelFactoryModel {

    @Binds
    abstract fun provideLaunchesModelFactory(launchesRepository: LaunchesRepository): LaunchesModelFactory
}