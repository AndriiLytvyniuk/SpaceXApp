package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.data.repository.di.AppContextModule
import alytvyniuk.com.data.repository.di.RepositoriesModule
import alytvyniuk.com.spacexapp.launchlist.LaunchesFragment
import alytvyniuk.com.spacexapp.statistics.StatisticsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoriesModule::class,
        AppContextModule::class]

)
interface AppComponent {

    fun inject(launchesFragment: LaunchesFragment)
    fun inject(launchesFragment: StatisticsFragment)
}
