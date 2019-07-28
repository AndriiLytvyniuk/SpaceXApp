package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.data.repository.di.AppContextModule
import alytvyniuk.com.data.repository.di.RepositoriesModule
import alytvyniuk.com.spacexapp.LaunchesFragment
import alytvyniuk.com.spacexapp.StatisticsFragment
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
