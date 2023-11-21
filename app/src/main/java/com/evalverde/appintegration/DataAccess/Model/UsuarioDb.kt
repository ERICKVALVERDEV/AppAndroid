package com.evalverde.appintegration.DataAccess.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("UsuarioEntity")
data class UsuarioEntity(
    @PrimaryKey(true) val id:Int =0,
    @ColumnInfo(name = "Usuario") val Usuario: String,
    @ColumnInfo(name = "Clave")val Clave: String,

)
