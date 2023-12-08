package com.evalverde.appintegration.onlineClient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
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
    var AbreviaturaDepartamento: String,
    var ColorDepartamento: String,
    var Agenda: Boolean,
    var FechaIngreso: Date,
    var IdZona: Int,
    var NombreZona: String,
    var CodeEncrypt: String,
    var QrCode: Boolean,
    var ImagenPerfil: String,
): Parcelable

