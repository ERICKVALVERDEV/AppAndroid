package com.evalverde.appintegration.onlineClient

import com.evalverde.appintegration.components.BrokerConnector
import com.evalverde.appintegration.onlineClient.model.GenUsuario

class GenUsuarioClientOperation {
    val m_ContainerKey: String = "CapaNegocio"
    val m_TargetComponent: String = "ILogin"
    suspend fun LoginSession(userLogin: String, clave: String): GenUsuario {
        try {
            var propertyName = ::LoginSession.name
            return BrokerConnector().DoBusinessOperation(m_ContainerKey,m_TargetComponent,propertyName,userLogin,clave)
        }catch (ex: Exception){
            ex.printStackTrace()
            throw  ex
        }
    }
}