package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.data.repository.di.AppContextModule
import alytvyniuk.com.data.repository.di.ImageLoaderModule
import alytvyniuk.com.data.repository.di.RepositoriesModule
import alytvyniuk.com.model.ImageLoader
import alytvyniuk.com.spacexapp.LaunchesModelFactory
import alytvyniuk.com.spacexapp.statistics.StatisticsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoriesModule::class,
        ImageLoaderModule::class,
        AppContextModule::class]

)
interface AppComponent {

    fun launchesModelFactory(): LaunchesModelFactory
    fun imageLoader(): ImageLoader
    fun inject(launchesFragment: StatisticsFragment)
}
