package com.evalverde.appintegration.dataAccess.Repository

    import com.evalverde.appintegration.dataAccess.interfaceDuo.IUsuarioLocalDao
import com.evalverde.appintegration.dataAccess.model.UsuarioEntity
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject
class UsuarioLocalRepository @Inject constructor(): IUsuarioLocalDao {

    override suspend fun Insert(usuarioEntity: UsuarioEntity) {
        UsuarioLocalRepository().Insert(usuarioEntity)
    }

    override suspend fun InsertAll(usuarioEntities: List<UsuarioEntity>) {
        UsuarioLocalRepository().InsertAll(usuarioEntities)
    }

    override suspend fun GetUsuarioLogin(usuario: String, clave: String): UsuarioEntity {
        return UsuarioLocalRepository().GetUsuarioLogin(usuario,clave)
    }

    override suspend fun DeleteAllUsuarioEntity() {
        return UsuarioLocalRepository().DeleteAllUsuarioEntity()
    }
}