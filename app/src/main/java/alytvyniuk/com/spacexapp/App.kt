package alytvyniuk.com.spacexapp

import alytvyniuk.com.spacexapp.di.AppComponent
import alytvyniuk.com.spacexapp.di.ComponentHolder
import android.app.Application

open class App : Application() {

    companion object {

        lateinit var component: AppComponent private set
    }

    override fun onCreate() {
        super.onCreate()
        component = ComponentHolder.getComponent(this)
    }
}
