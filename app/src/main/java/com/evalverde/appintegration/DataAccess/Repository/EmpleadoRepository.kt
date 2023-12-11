package com.evalverde.appintegration.dataAccess.Repository

import com.evalverde.appintegration.dataAccess.interfaceDuo.IEmpleadoDuo
import com.evalverde.appintegration.dataAccess.model.EmpleadoEntity
import javax.inject.Inject

class EmpleadoRepository @Inject constructor(private val iEmpleado: IEmpleadoDuo) {

    suspend fun Insert(usuarioEntity: EmpleadoEntity) {
        iEmpleado.Insert(usuarioEntity)
    }

    suspend fun InsertAll(usuarioEntities: List<EmpleadoEntity>) {
        iEmpleado.InsertAll(usuarioEntities)
    }

    suspend fun GetEmpleado(cedula: String): EmpleadoEntity {
        return iEmpleado.GetEmpleado(cedula)
    }
    suspend fun GetEmpleados(): List<EmpleadoEntity> {
        return iEmpleado.GetEmpleados()
    }

    suspend fun DeleteAllEmpleadoEntity() {
        return iEmpleado.DeleteAllUsuarioEntity()
    }


}