package com.evalverde.appintegration.dataAccess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evalverde.appintegration.dataAccess.interfaceDuo.IEmpleadoDuo
import com.evalverde.appintegration.dataAccess.interfaceDuo.IUsuarioLocalDao
import com.evalverde.appintegration.dataAccess.model.EmpleadoEntity
import com.evalverde.appintegration.dataAccess.model.UsuarioEntity

@Database(entities = arrayOf(UsuarioEntity::class,EmpleadoEntity::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iUsuarioLocalDao(): IUsuarioLocalDao
    abstract fun iEmpleadoDao(): IEmpleadoDuo
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,"BaseAppI.db3")
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}