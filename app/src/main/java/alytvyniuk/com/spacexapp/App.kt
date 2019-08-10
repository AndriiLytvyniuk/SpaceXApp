package alytvyniuk.com.spacexapp

import alytvyniuk.com.spacexapp.di.AppComponent
import alytvyniuk.com.spacexapp.di.DaggerAppComponent
import android.app.Application

open class App : Application() {

    companion object {

        lateinit var component: AppComponent private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
    }
}
