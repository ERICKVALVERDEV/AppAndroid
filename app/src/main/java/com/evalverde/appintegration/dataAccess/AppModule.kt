package com.evalverde.appintegration.dataAccess

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun ProvideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun ProvideUsuarioLocal(db: AppDatabase) = db.iUsuarioLocalDao()
    @Singleton
    @Provides
    fun ProvideEmpleado(db: AppDatabase) = db.iEmpleadoDao()
}