package com.example.agenciaculturaydeportes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.agenciaculturaydeportes.databinding.ActivityEventosDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_eventos_detail.*


class EventosDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_detail)


        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("evento").child(
            key.toString()
        )

       val binding = ActivityEventosDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val evento:Evento? = dataSnapshot.getValue(Evento::class.java)
                if (evento != null) {
                    nameTextView.text = evento.name.toString()
                    descriptionTextView.text = evento.description.toString()
                    images(evento.url.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })



        binding.imageView3.setOnClickListener {
            val formulario = Intent(this, FormulariosActivity::class.java)
            startActivity(formulario)
        }



    }

    private  fun images(link: String){
        Glide.with(this)
            .load(link)
            .into(posterImgeView)

        Glide.with(this)
            .load(link)
            .into(backgroundImageView)
    }


}