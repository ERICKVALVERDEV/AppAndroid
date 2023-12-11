package com.evalverde.appintegration.dataAccess.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.evalverde.appintegration.dataAccess.Converters
import java.util.Date

@Entity("EmpleadoEntity")
@TypeConverters(Converters::class)
data class EmpleadoEntity(
    @PrimaryKey(true)
    @ColumnInfo(name = "Id") var Id:Int =0,
    @ColumnInfo(name = "IdEmpleado") var IdEmpleado: Int,
    @ColumnInfo(name = "IdCargo")  var IdCargo: Int,
    @ColumnInfo(name = "IdDepartamento")  var IdDepartamento: Int,
    @ColumnInfo(name = "NumeroIdentificacion")  var NumeroIdentificacion: String,
    @ColumnInfo(name = "Nombres")  var Nombres: String,
    @ColumnInfo(name = "Apellidos")  var Apellidos: String,
    @ColumnInfo(name = "Activo")  var Activo: Boolean,
    @ColumnInfo(name = "NombreDepartamento")  var NombreDepartamento: String,
    @ColumnInfo(name = "NombreCargo")  var NombreCargo: String,
    @ColumnInfo(name = "Extension")  var Extension: String,
    @ColumnInfo(name = "CorreoElectronico")  var CorreoElectronico: String,
    @ColumnInfo(name = "AbreviaturaDepartamento")  var AbreviaturaDepartamento: String,
    @ColumnInfo(name = "ColorDepartamento")  var ColorDepartamento: String,
    @ColumnInfo(name = "Agenda")  var Agenda: Boolean,
    @ColumnInfo(name = "FechaIngreso")  var FechaIngreso: Date,
    @ColumnInfo(name = "IdZona")  var IdZona: Int,
    @ColumnInfo(name = "NombreZona")  var NombreZona: String,
    @ColumnInfo(name = "CodeEncrypt")  var CodeEncrypt: String,
    @ColumnInfo(name = "QrCode")  var QrCode: Boolean,
    @ColumnInfo(name = "ImagenPerfil")  var ImagenPerfil: String)
