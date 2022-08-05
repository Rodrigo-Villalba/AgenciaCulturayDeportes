package com.example.agenciaculturaydeportes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agenciaculturaydeportes.databinding.ActivityFormulariosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_formularios.*
import kotlinx.android.synthetic.main.activity_main.addressEditText
import kotlinx.android.synthetic.main.activity_main.dniEditText
import kotlinx.android.synthetic.main.activity_main.nameEditText
import kotlinx.android.synthetic.main.activity_main.phoneEditText

class FormulariosActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFormulariosBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //base datos
        val bundle:Bundle? = intent.extras
        val provider:String? = bundle?.getString("provider")
        //fin base de datos

        binding = ActivityFormulariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.updateProfileAppCompatButton.setOnClickListener {
            //val deporte  = binding.deporteEditText.text.toString()



            //base de datos

            db.collection("deportes").document().set(
                hashMapOf("provider" to provider,
                    "name" to nameEditText.text.toString(),
                    "dni" to dniEditText.text.toString(),
                    "phone" to phoneEditText.text.toString(),
                    "address" to addressEditText.text.toString(),
                    "deporte" to deporteEditText.text.toString(),
                    "centro_deportivo" to centroDeportivoEditText.text.toString(),
                    "evento" to eventoEditText.text.toString()
                )
            )


        }


    }




}

