package com.evalverde.appintegration.ui.menu

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.evalverde.appintegration.R
import com.evalverde.appintegration.databinding.FragmentCredentialQrBinding
import com.evalverde.appintegration.onlineClient.model.GenEmpleado

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
        arguments?.let {
            println("C.I ${param1?.NumeroIdentificacion}")
            param1 = it.getParcelable(argEmpleado)
            binding.txtCargo.text= param1?.NombreCargo
            binding.txtCedula.text= "C.I ${param1?.NumeroIdentificacion}"
            binding.txtNombreEmpleado.text= param1?.Nombres
            binding.txtDepartamento.text= param1?.NombreDepartamento
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_qr, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun nuevaCredencial(empleado: GenEmpleado?) =
            CredentialQr().apply {
                arguments = Bundle().apply {
                    println("RESULTOADO "+empleado)
                    putParcelable(argEmpleado, empleado as Parcelable)
                }
            }
    }
}