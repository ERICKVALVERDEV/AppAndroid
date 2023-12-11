package com.evalverde.appintegration.dataAccess.interfaceDuo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evalverde.appintegration.dataAccess.model.EmpleadoEntity
@Dao
interface IEmpleadoDuo {
    @Insert
    suspend fun Insert(usuarioEntity: EmpleadoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAll(usuarioEntities: List<EmpleadoEntity>)

    @Query("SELECT * FROM EmpleadoEntity WHERE NumeroIdentificacion = :cedula")
    suspend fun GetEmpleado(cedula: String): EmpleadoEntity

    @Query("SELECT * FROM EmpleadoEntity")
    suspend fun GetEmpleados(): List<EmpleadoEntity>

    @Query("DELETE FROM EmpleadoEntity")
    suspend fun DeleteAllUsuarioEntity()
}