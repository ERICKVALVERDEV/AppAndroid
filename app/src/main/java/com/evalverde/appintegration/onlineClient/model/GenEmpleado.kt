package com.evalverde.appintegration.onlineClient.model
data class GenEmpleado(
    var IdEmpleado: Int,
    var IdCargo: Int,
    var IdDepartamento: Int,
    var NumeroIdentificacion: String,
    var Nombres: String,
    var Apellidos: String,
    var Activo: Boolean,
    var NombreDepartamento: String,
    var NombreCargo: String,
    var Extension: String,
    var CorreoElectronico: String,
    var Agenda: Boolean,
    var ExtensionInt: Int,
    var FechaIngreso: String,
    var IdZona: Int,
    var NombreZona: String,
    var CodeEncrypt: String,
    var QrCode: Boolean
)

