package com.evalverde.appintegration.ui.menu

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.evalverde.appintegration.R
import com.evalverde.appintegration.components.DisplayAlert
import com.evalverde.appintegration.databinding.ActivityMenuBinding
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import com.evalverde.appintegration.ui.CaptureActivityPortrait
import com.evalverde.appintegration.ui.menu.model.MenuViewModel
import com.google.zxing.integration.android.IntentIntegrator

class Menu: AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    private val menuViewModel : MenuViewModel by viewModels()
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Declaramos el observador
        menuViewModel.menuResult.observe(this, Observer {
            val result = it ?: return@Observer
            if(result.isValid){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragmentCredential, CredentialQr())
                fragmentTransaction.commit()
                var response = result.responseObject as ArrayList<GenEmpleado>
                println("PPPPPPPPPPPPPPPPPPPP2222222"+ response.javaClass.simpleName)
                var resp = (response as? ArrayList<GenEmpleado>)?.firstOrNull()
                println("PPPPPPPPPPPPPPPPPPPP2222222333333")

                CredentialQr.nuevaCredencial(resp)
            }else{
                DisplayAlert(this, "Error"," "+result.errorText).show()
            }
        })
        binding.btnScannerCredentials.setOnClickListener(View.OnClickListener {
            startScanner()
        })

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no hay permisos, solicitarlos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        binding.floatingButtonExp.setOnClickListener(View.OnClickListener {
            //Aqui se debe sincronizar los datos
        })

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
                DisplayAlert(this, "Alerta","Debe tener acceso a la cámara").show()
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
                menuViewModel.obtenerEmpleadoData(result.contents)
                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}