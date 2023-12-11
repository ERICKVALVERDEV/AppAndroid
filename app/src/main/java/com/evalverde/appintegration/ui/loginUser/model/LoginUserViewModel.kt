package com.evalverde.appintegration.ui.loginUser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evalverde.appintegration.dataAccess.Repository.UsuarioLocalRepository
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.onlineClient.GenUsuarioClientOperation
import com.evalverde.appintegration.onlineClient.model.GenUsuario
import com.evalverde.appintegration.onlineClient.model.toOffline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class LoginUserViewModel @Inject constructor(private val getUsuarioRepository : UsuarioLocalRepository)  : ViewModel() {
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

                    val user = GenUsuarioClientOperation().LoginSession(usuario, clave)
                    isLoginSuccessful = (user.CodigoUsuario == usuario)
                    if(isLoginSuccessful){
                        //Guardamos el usuario localmente
                        saveLogin(user)
                    }

                    // Actualizar LiveData en el hilo principal
                    withContext(Dispatchers.Main) {
                        _loginResult.value = ResultViewModel(isLoginSuccessful, user,null)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                // Actualizar LiveData en el hilo principal
                withContext(Dispatchers.Main) {
                    _loginResult.value = ResultViewModel(false,null, ex.message)
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
        getUsuarioRepository.Insert(usuarioEntity)


    }

}