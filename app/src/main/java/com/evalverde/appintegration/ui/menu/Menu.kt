package com.evalverde.appintegration.ui.menu

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.evalverde.appintegration.databinding.ActivityMenuBinding
import com.google.zxing.integration.android.IntentIntegrator

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
        .setPrompt("CredenciaL QR IPSP")
        .setTorchEnabled(true)
        .setBeepEnabled(true)
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
                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
            }
//            finish()
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}