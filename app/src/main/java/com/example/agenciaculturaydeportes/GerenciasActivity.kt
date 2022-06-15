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

        binding.mapaButton.setOnClickListener {
            val mapa = Intent(this, MapaActivity::class.java)
            startActivity(mapa)
        }


    }
}