package com.evalverde.appintegration.dataAccess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evalverde.appintegration.dataAccess.interfaceDuo.IUsuarioLocalDao
import com.evalverde.appintegration.dataAccess.model.UsuarioEntity

@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iUsuarioLocalDao(): IUsuarioLocalDao
    companion object{
        @Volatile
        lateinit var db: AppDatabase

        fun getDatabase(context: Context):AppDatabase{
            return synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"BaseAppI")
                    .build()
                db = instance
                instance
            }
        }

    }

}