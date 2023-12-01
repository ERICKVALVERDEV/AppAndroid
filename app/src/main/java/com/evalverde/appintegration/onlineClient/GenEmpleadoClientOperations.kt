package com.evalverde.appintegration.onlineClient

import com.evalverde.appintegration.components.BrokerConnector
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import com.evalverde.appintegration.onlineClient.model.GenUsuario
import java.time.LocalDate
import java.util.Date

class GenEmpleadoClientOperations {
    val m_ContainerKey: String = "CapaNegocio"
    val m_TargetComponent: String = "ICodigosAcceso"

    suspend fun ConsultarEmpleadoXAcceso(cedula: String, fecha: LocalDate): GenEmpleado {
        try {
            var propertyName = ::ConsultarEmpleadoXAcceso.name
            return BrokerConnector().DoBusinessOperation(m_ContainerKey,m_TargetComponent,propertyName,cedula,fecha)
        }catch (ex: Exception){
            ex.printStackTrace()
            throw  ex
        }
    }
}