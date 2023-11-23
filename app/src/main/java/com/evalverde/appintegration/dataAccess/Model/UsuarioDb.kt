package com.evalverde.appintegration.dataAccess.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("UsuarioEntity")
data class UsuarioEntity(
    @PrimaryKey(true) val Id:Int =0,
    @ColumnInfo(name = "IdUsuario") var IdUsuario: Int,
    @ColumnInfo(name = "Identificacion") var Identificacion: Int,
    @ColumnInfo(name = "CodigoUsuario") var CodigoUsuario: String,
    @ColumnInfo(name = "Nombres") var Nombres: String,
    @ColumnInfo(name = "Apellidos") var Apellidos: String,
    @ColumnInfo(name = "Correo") var Correo: String,
    @ColumnInfo(name = "NombreUsuario") var NombreUsuario: String,
    @ColumnInfo(name = "Clave") var Clave: String,
    @ColumnInfo(name = "IdZona") var IdZona: Int,
    @ColumnInfo(name = "IdDepartamento") var IdDepartamento: Int,
    @ColumnInfo(name = "NombreDepartamento") var NombreDepartamento: Int,
    @ColumnInfo(name = "IdRol") var IdRol: Int,
    @ColumnInfo(name = "NombreRol") var NombreRol: Int,
    @ColumnInfo(name = "Activo") var Activo: Int,
    @ColumnInfo(name = "UsuarioAd") var UsuarioAd: Int,
)
