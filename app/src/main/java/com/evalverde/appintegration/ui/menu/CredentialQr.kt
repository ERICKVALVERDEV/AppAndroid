package com.evalverde.appintegration.ui.menu

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.evalverde.appintegration.R
import com.evalverde.appintegration.databinding.ActivityMenuBinding
import com.evalverde.appintegration.databinding.FragmentCredentialQrBinding
import com.evalverde.appintegration.onlineClient.model.GenEmpleado
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val argEmpleado = "genEmpleado"

class CredentialQr : Fragment() {
    private lateinit var binding : FragmentCredentialQrBinding
    // TODO: Rename and change types of parameters
    private var param1: GenEmpleado? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @OptIn(ExperimentalEncodingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_credential_qr, container, false)
        binding = FragmentCredentialQrBinding.inflate(inflater, container, false)
        arguments?.let {
            println("C.I ${param1?.NumeroIdentificacion}")
            param1 = it.getParcelable(argEmpleado)
            binding.txtCargo.text= param1?.NombreCargo
            binding.txtCedula.text= "C.I ${param1?.NumeroIdentificacion}"
            binding.txtNombreEmpleado.text= param1?.Nombres +" "+ param1?.Apellidos
            binding.txtDepartamento.text= param1?.NombreDepartamento
            binding.txtDepartamento.setBackgroundColor(Color.parseColor("#" + param1?.ColorDepartamento))
            val ingLength = param1?.ImagenPerfil?.length?:0
            println(param1?.ImagenPerfil)
            val imgbyte = Base64.decode(param1?.ImagenPerfil?: "", 0,ingLength)
            println(imgbyte)
            val bitmap = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.size)
            println("se carga la imagen")
            println(bitmap)

            binding.imgPerfil.setImageBitmap(bitmap)
        }
        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun nuevaCredencial(empleado: GenEmpleado?) =
            CredentialQr().apply {
                arguments = Bundle().apply {
                    putParcelable(argEmpleado, empleado as Parcelable)
                }
            }
    }
}