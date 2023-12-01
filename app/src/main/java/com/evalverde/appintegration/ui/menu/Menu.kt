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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.evalverde.appintegration.R
import com.evalverde.appintegration.databinding.ActivityMenuBinding
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.onlineClient.GenEmpleadoClientOperations
import com.evalverde.appintegration.onlineClient.GenUsuarioClientOperation
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Menu: AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScannerCredentials.setOnClickListener(View.OnClickListener {
            initScanner()
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
        } else {
            // Si ya hay permisos, iniciar el escáner
            startScanner()
        }
    }
    private fun startScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("Escanea el código QR")
        integrator.initiateScan()
    }
    private fun initScanner(){
        IntentIntegrator(this)
        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        .setOrientationLocked(true)
        .setPrompt("Credencial QR IPSP")
        .initiateScan()

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
                // Permiso denegado, puedes mostrar un mensaje al usuario o tomar alguna acción
//                finish()
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    MostrarCredencial(result.contents)
                }
                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun MostrarCredencial(codigoQr: String){
        lifecycleScope.launch{
            try {
                var fechaHora = LocalDate.now()
                var empleado = GenEmpleadoClientOperations().ConsultarEmpleadoXAcceso("",fechaHora)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragmentCredential, CredentialQr())
                fragmentTransaction.commit()
            }catch (ex: Exception){
                withContext(Dispatchers.Main) {
                    val builder = AlertDialog.Builder(this@Menu)
                    builder.setTitle("Error")
                    builder.setMessage(ex.message).show()
                }
            }
        }
    }
}