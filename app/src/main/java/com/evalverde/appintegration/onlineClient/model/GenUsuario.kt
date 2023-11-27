package com.evalverde.appintegration.onlineClient.model

import com.evalverde.appintegration.dataAccess.model.UsuarioEntity

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
                      var NombreDepartamento: String,
                      var IdRol: Int,
                      var NombreRol: String,
                      var Activo: Boolean,
                      var UsuarioAd: String,)

fun GenUsuario.toOffline() = UsuarioEntity(0,IdUsuario,Identificacion,CodigoUsuario,Nombres,Apellidos,Correo,NombreUsuario,
    Clave,IdZona,IdDepartamento,NombreDepartamento,IdRol,NombreRol,Activo,UsuarioAd)