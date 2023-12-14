package com.evalverde.appintegration.ui.menu

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.evalverde.appintegration.R
import com.evalverde.appintegration.components.DisplayAlert
import com.evalverde.appintegration.components.IsConnectivityNetwork
import com.evalverde.appintegration.components.LoadingDialog
import com.evalverde.appintegration.components.SharedPreferencesManager
import com.evalverde.appintegration.dataAccess.model.EmpleadoEntity
import com.evalverde.appintegration.databinding.ActivityMenuBinding
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import com.evalverde.appintegration.ui.CaptureActivityPortrait
import com.evalverde.appintegration.ui.loginUser.LoginUser
import com.evalverde.appintegration.ui.menu.model.MenuViewModel
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Menu : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    private val menuViewModel: MenuViewModel by viewModels()
    val loadingDialog = LoadingDialog(this)

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.edtQr.requestFocus()
        //Declaramos el observador
        menuViewModel.menuResult.observe(this, Observer {
            val result = it ?: return@Observer
            if (result.isValid) {
//                val response = result.responseObject as ArrayList<GenEmpleado>
                if (result.responseObject != null) {
                    val resp = result.responseObject as GenEmpleado
//                val resp = response.firstOrNull()

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.add(
                        R.id.fragmentCredential,
                        CredentialQr.nuevaCredencial(resp)
                    )
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    binding.floatingButtonExp.visibility = View.GONE
                } else {
                    DisplayAlert(this, "Mensaje", "No existe registro del empleado.").show()
                }
            } else {
                DisplayAlert(this, "Error", " " + result.errorText).show()
            }
            loadingDialog.dimissDialog()
//            binding.edtQr.requestFocus()
        })
        menuViewModel.dataSync.observe(this, Observer {
            val result = it ?: return@Observer
            if (result.isValid) {
                DisplayAlert(this, "Mensaje", "Datos sincronizados correctamente...").show()
            } else {
                DisplayAlert(this, "Mensaje", result.errorText!!).show()
            }
            loadingDialog.dimissDialog()
        })

        binding.edtQr.setOnEditorActionListener { edtView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_NULL) {
                println("adsadasda" + edtView.text)
//                menuViewModel.obtenerEmpleadoData(text.toString())
                edtView.text = ""
                edtView.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.edtQr.addTextChangedListener({ text, start, count, after ->
            if (!text.isNullOrEmpty()) {
            }
        })

        binding.btnScannerCredentials.setOnClickListener(View.OnClickListener {
            startScanner()
        })

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no hay permisos, solicitarlos
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA),CAMERA_PERMISSION_REQUEST_CODE)
        }

        binding.floatingButtonExp.setOnClickListener(View.OnClickListener {
            loadingDialog.startLoadingDialog()
            menuViewModel.sincronizationData()
        })

        binding.floatingButtonExpCerrar.setOnClickListener(View.OnClickListener {
            SharedPreferencesManager(this).closeSession()
            val i = Intent(this, LoginUser::class.java)
            startActivity(i)
            finish()
        })

    }

    override fun onResume() {
        super.onResume()
//        binding.floatingButtonExp.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
//        binding.floatingButtonExp.visibility = View.VISIBLE
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        println("EEEEEEEEEEE" + visible)
    }

    private fun startScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setCaptureActivity(CaptureActivityPortrait::class.java)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("Escanea el código QR")
        integrator.initiateScan()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, iniciar el escáner
                startScanner()

            } else {
                DisplayAlert(this, "Alerta", "Debe tener acceso a la cámara").show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Manejar el resultado del escaneo aquí
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                if(IsConnectivityNetwork(this)){
                    loadingDialog.startLoadingDialog()
                    menuViewModel.obtenerEmpleadoData(result.contents)
                }else{
                    DisplayAlert(this,"Mensaje","Necesita una conexión estable a red.").show()
                }
//                menuViewModel.obtenerEmpleadoOnline(result.contents)
//                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun sincronizarDatos() {

    }

}