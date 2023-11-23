package com.evalverde.appintegration.dataAccess.interfaceDuo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evalverde.appintegration.dataAccess.model.UsuarioEntity

@Dao
interface IUsuarioLocalDao {
    @Insert
    suspend fun Insert(usuarioEntity: UsuarioEntity)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun InsertAll(usuarioEntities: List<UsuarioEntity>)

    @Query("SELECT * FROM UsuarioEntity WHERE CodigoUsuario = :usuario AND Clave = :clave")
    suspend fun GetUsuarioLogin(usuario:String, clave: String): UsuarioEntity?

    @Query("DELETE FROM UsuarioEntity ")
    suspend fun  DeleteAllUsuarioEntity()
}