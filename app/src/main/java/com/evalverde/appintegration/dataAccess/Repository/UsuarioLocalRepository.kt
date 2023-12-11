package com.evalverde.appintegration.dataAccess.Repository

    import com.evalverde.appintegration.dataAccess.interfaceDuo.IUsuarioLocalDao
    import com.evalverde.appintegration.dataAccess.model.UsuarioEntity
    import javax.inject.Inject

class UsuarioLocalRepository @Inject constructor(private val iUsuarioLocalDao: IUsuarioLocalDao) {

    suspend fun Insert(usuarioEntity: UsuarioEntity) {
        iUsuarioLocalDao.Insert(usuarioEntity)
    }

    suspend fun InsertAll(usuarioEntities: List<UsuarioEntity>) {
        iUsuarioLocalDao.InsertAll(usuarioEntities)
    }

    suspend fun GetUsuarioLogin(usuario: String, clave: String): UsuarioEntity {
        return iUsuarioLocalDao.GetUsuarioLogin(usuario,clave)
    }

    suspend fun DeleteAllUsuarioEntity() {
        return iUsuarioLocalDao.DeleteAllUsuarioEntity()
    }

    suspend fun GetUsuario(): List<UsuarioEntity> {
        return iUsuarioLocalDao.GetUsuario()
    }

}