package com.evalverde.appintegration.ui.menu.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evalverde.appintegration.dataAccess.Repository.EmpleadoRepository
import com.evalverde.appintegration.dataAccess.Repository.UsuarioLocalRepository
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.onlineClient.GenEmpleadoClientOperations
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import com.evalverde.appintegration.onlineClient.model.toOffline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class MenuViewModel @Inject constructor(private val getEmpleadoRepository : EmpleadoRepository)  : ViewModel() {

    private val _menuResult = MutableLiveData<ResultViewModel>()
    val menuResult: LiveData<ResultViewModel> get() = _menuResult
    private val _dataSync = MutableLiveData<ResultViewModel>()
    val dataSync: LiveData<ResultViewModel> get() =_dataSync

    @RequiresApi(Build.VERSION_CODES.O)
    fun sincronizationData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //Eliminamos los datos almacenados
                getEmpleadoRepository.DeleteAllEmpleadoEntity()
//                val empleados = GenEmpleadoClientOperations().ConsultarEmpleadoXAcceso(null, LocalDate.now())
                val empleados = GenEmpleadoClientOperations().ObtenerEmpleado(null)
                if(empleados.any())
                    getEmpleadoRepository.InsertAll(empleados.toOffline())

                withContext(Dispatchers.Main) {
                    _dataSync.value = ResultViewModel(true, null,null)
                }
            } catch (ex: Exception) {
                println(ex.message)
                withContext(Dispatchers.Main) {
                    _dataSync.value = ResultViewModel(false, null,ex.message)
                }
            }
        }
    }

    fun obtenerEmpleadoData(code: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var fechaHoy = LocalDate.now()
                var empleados = withContext(Dispatchers.IO) {
                    GenEmpleadoClientOperations().ObtenerEmpleadoCodeEncrypt(code)
                }
                _menuResult.postValue(ResultViewModel(true, empleados))
            } catch (ex: Exception) {
                _menuResult.value =ResultViewModel(false,null, ex.message.toString())
            }
        }
    }
    fun obtenerEmpleadoOnline(code: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var empleados = withContext(Dispatchers.IO) {
                    GenEmpleadoClientOperations().ObtenerEmpleado(null)
                }
                _menuResult.postValue(ResultViewModel(true, empleados))
            } catch (ex: Exception) {
                _menuResult.value =ResultViewModel(false,null, ex.message.toString())
            }
        }
    }


}