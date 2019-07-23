package alytvyniuk.com.spacexapp.di

import alytvyniuk.com.data.repository.di.AppContextModule
import android.app.Application

object ComponentHolder {

    fun getComponent(application: Application) : AppComponent {
        return DaggerAppComponent.builder().appContextModule(AppContextModule(application)).build()
    }
}
