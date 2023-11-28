package com.evalverde.appintegration.dataAccess.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evalverde.appintegration.onlineClient.model.GenUsuario

@Entity("UsuarioEntity")
data class UsuarioEntity(
    @PrimaryKey(true)
    @ColumnInfo(name = "Id") var Id:Int =0,
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
    @ColumnInfo(name = "NombreDepartamento") var NombreDepartamento: String,
    @ColumnInfo(name = "IdRol") var IdRol: Int,
    @ColumnInfo(name = "NombreRol") var NombreRol: String,
    @ColumnInfo(name = "Activo") var Activo: Boolean,
    @ColumnInfo(name = "UsuarioAd") var UsuarioAd: String,
)

fun UsuarioEntity.toOnline() = GenUsuario(IdUsuario,Identificacion,CodigoUsuario,Nombres,Apellidos,Correo,NombreUsuario,
    Clave,IdZona,IdDepartamento,NombreDepartamento,IdRol,NombreRol,Activo,UsuarioAd)
