package com.evalverde.appintegration.ui.menu.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.onlineClient.GenEmpleadoClientOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MenuViewModel : ViewModel() {

    private val _menuResult = MutableLiveData<ResultViewModel>()
    val menuResult: LiveData<ResultViewModel> get() = _menuResult

    @RequiresApi(Build.VERSION_CODES.O)
    fun sincronizationData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var empleados =
                    GenEmpleadoClientOperations().ConsultarEmpleadoXAcceso(null, LocalDate.now())
                //Aqui va la lógica de guardar offline
            } catch (ex: Exception) {
                println(ex.message)
            }
        }
    }

    fun obtenerEmpleadoData(cedula: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var fechaHoy = LocalDate.now()
                var empleados = withContext(Dispatchers.IO) {
                    GenEmpleadoClientOperations().ConsultarEmpleadoXAcceso("0932499635", fechaHoy)
                }
                _menuResult.postValue(ResultViewModel(true, empleados))
            } catch (ex: Exception) {
                _menuResult.value =ResultViewModel(false,null, ex.message.toString())
            }
        }
    }
}