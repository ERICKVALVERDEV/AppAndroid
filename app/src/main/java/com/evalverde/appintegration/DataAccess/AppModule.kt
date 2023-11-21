package com.evalverde.appintegration.DataAccess

import android.content.Context
import com.evalverde.appintegration.DataAccess.Interface.IUsuarioLocalDao
import com.evalverde.appintegration.DataAccess.Repository.UsuarioLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {

    @Provides
    @Singleton
    fun ProvideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun ProvideUsuarioLocal(db: AppDatabase) = db.iUsuarioLocalDao()

    @Provides
    @Singleton
    fun ProvideUsuarioLocalRepository(iusuarioLocal: IUsuarioLocalDao)= UsuarioLocalRepository(iusuarioLocal)
}