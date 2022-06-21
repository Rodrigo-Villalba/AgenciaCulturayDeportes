package com.example.agenciaculturaydeportes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agenciaculturaydeportes.databinding.ActivityGerenciasBinding


class GerenciasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val binding = ActivityGerenciasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.culturaButton.setOnClickListener {
            val cultura = Intent(this, CulturaActivity::class.java)
            startActivity(cultura)
        }

        binding.deportesButton.setOnClickListener {
            val deportes = Intent(this, DeportesActivity::class.java)
            startActivity(deportes)
        }

        binding.actividadFisicaButton.setOnClickListener {
            val actividadFisica = Intent(this, ActividadFisicaActivity::class.java)
            startActivity(actividadFisica)
        }



    }
}