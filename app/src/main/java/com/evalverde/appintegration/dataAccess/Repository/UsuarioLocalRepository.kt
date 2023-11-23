package com.evalverde.appintegration.dataAccess.Repository

    import com.evalverde.appintegration.dataAccess.interfaceDuo.IUsuarioLocalDao
import com.evalverde.appintegration.dataAccess.model.UsuarioEntity
import javax.inject.Inject

class UsuarioLocalRepository @Inject constructor(private val usuarioLocal: IUsuarioLocalDao?) {

    suspend fun InsertUsuarioLocal(usuarioDb: UsuarioEntity) {
        usuarioLocal?.Insert(usuarioDb)
    }

    suspend fun getUserCredential(usuario: String, clave: String): UsuarioEntity?{
        return usuarioLocal?.GetUsuarioLogin(usuario,clave)
    }

    suspend fun clearUsuarioEntity(): Unit? {
        return usuarioLocal?.DeleteAllUsuarioEntity()
    }
}