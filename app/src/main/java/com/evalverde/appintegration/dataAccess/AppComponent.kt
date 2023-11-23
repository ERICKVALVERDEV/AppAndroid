package com.evalverde.appintegration.dataAccess

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: Application) // Esto permite la inyección en la clase Application
}