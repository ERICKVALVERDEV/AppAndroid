package com.evalverde.appintegration.onlineClient

import com.evalverde.appintegration.components.BrokerConnector
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import com.evalverde.appintegration.onlineClient.model.GenUsuario
import java.time.LocalDate
import java.util.Date

class GenEmpleadoClientOperations {
    val m_ContainerKey: String = "CapaNegocio"
    val m_TargetComponent: String = "IDepartamento"

    suspend fun ConsultarEmpleadoXAcceso(cedula: String?, fecha: LocalDate): List<GenEmpleado> {
        var propertyName = ::ConsultarEmpleadoXAcceso.name
        return BrokerConnector().DoBusinessOperation(m_ContainerKey,"ICodigosAcceso",propertyName,cedula,fecha)

    }
}