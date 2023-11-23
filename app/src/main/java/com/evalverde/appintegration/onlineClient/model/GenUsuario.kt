package com.evalverde.appintegration.onlineClient.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GenUsuario(var IdUsuario: Int,
                      var Identificacion: Int,
                      var CodigoUsuario: String,
                      var Nombres: String,
                      var Apellidos: String,
                      var Correo: String,
                      var NombreUsuario: String,
                      var Clave: String,
                      var IdZona: Int,
                      var IdDepartamento: Int,
                      var NombreDepartamento: Int,
                      var IdRol: Int,
                      var NombreRol: Int,
                      var Activo: Int,
                      var UsuarioAd: Int,)
