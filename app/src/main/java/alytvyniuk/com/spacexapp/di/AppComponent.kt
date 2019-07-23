package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.data.repository.di.AppContextModule
import alytvyniuk.com.data.repository.di.RepositoriesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoriesModule::class,
        AppContextModule::class]

)
interface AppComponent {


}
