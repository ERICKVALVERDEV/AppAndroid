package com.evalverde.appintegration.ui.loginUser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evalverde.appintegration.dataAccess.AppDatabase
import com.evalverde.appintegration.dataAccess.AppDatabase.Companion.db
import com.evalverde.appintegration.dataAccess.Repository.UsuarioLocalRepository
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.login.data.LoginRepository
import com.evalverde.appintegration.onlineClient.GenUsuarioClientOperation
import com.evalverde.appintegration.onlineClient.model.GenUsuario
import com.evalverde.appintegration.onlineClient.model.toOffline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginUserViewModel : ViewModel() {
    companion object {
        lateinit var database: AppDatabase
    }
    private val _loginResult = MutableLiveData<ResultViewModel>()
    val loginResult: LiveData<ResultViewModel> get() = _loginResult

    private val _userError = MutableLiveData<String>()
    val userError: LiveData<String> get() = _userError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    fun performLogin(usuario: String, clave: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var isLoginSuccessful = false
//                validateUser(usuario)
//                validatePassword(clave)
                if (!usuario.isBlank() && !clave.isBlank()) {
                    var err: String? = null
                    try {
                        val user = GenUsuarioClientOperation().LoginSession(usuario, clave)
                        isLoginSuccessful = (user.CodigoUsuario == usuario)
                        if(isLoginSuccessful){
                            //Guardamos el usuario localmente
                            saveLogin(user)
                        }
                    } catch (ex: Exception) {
                        err = ex.message.toString()
                        isLoginSuccessful = false
                    }
                    // Actualizar LiveData en el hilo principal
                    withContext(Dispatchers.Main) {
                        _loginResult.value = ResultViewModel(isLoginSuccessful, err)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                // Actualizar LiveData en el hilo principal
                withContext(Dispatchers.Main) {
                    _loginResult.value = ResultViewModel(false, ex.message)
                }
            }
        }
    }

    fun validateUser(usuario: String) {
        _userError.value = if (usuario.isBlank()) "Usuario es Obligatorio" else null
    }

    fun validatePassword(password: String) {
        _passwordError.value = if (password.isBlank()) "Contraseña es Obligatorio" else null
    }

    suspend fun saveLogin(genUsuario: GenUsuario){
        //convertimos el modelo
        val usuarioEntity = genUsuario.toOffline()
//        db.iUsuarioLocalDao().Insert(usuarioEntity)

    }

}