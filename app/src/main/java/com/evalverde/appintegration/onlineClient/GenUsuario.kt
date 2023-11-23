package com.evalverde.appintegration.onlineClient

import com.evalverde.appintegration.components.BrokerConnector

class GenUsuario {
    val m_ContainerKey: String = "CapaNegocio"
    val m_TargetComponent: String = "IUsuarioServerOperations"
    suspend fun LoginSession(userLogin: String, clave: String): GenUsuario {
        var propertyName = ::LoginSession.name
        return BrokerConnector().DoBusinessOperation(m_ContainerKey,m_TargetComponent,propertyName,userLogin,clave)
    }
}