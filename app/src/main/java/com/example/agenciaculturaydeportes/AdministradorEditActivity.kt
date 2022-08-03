package com.example.agenciaculturaydeportes

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_administrador_edit.*


class AdministradorEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador_edit)



        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("evento").child(
            key.toString()
        )

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val evento:Evento? = dataSnapshot.getValue(Evento::class.java)
                if (evento != null) {
                    nameEditText.text = Editable.Factory.getInstance().newEditable(evento.name)
                    dateEditText.text = Editable.Factory.getInstance().newEditable(evento.date)
                    descriptionEditText.text = Editable.Factory.getInstance().newEditable(evento.description)
                    urlEditText.text = Editable.Factory.getInstance().newEditable(evento.url)
                    //uploadImageView.text = Editable.Factory.getInstance().newEditable(evento.link)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        saveButton.setOnClickListener {

            val name : String = nameEditText.text.toString()
            val date : String = dateEditText.text.toString()
            val description: String = descriptionEditText.text.toString()
            val url: String = urlEditText.toString()
            //val link: String = uploadImageView.toString()

            myRef.child("name").setValue(name)
            myRef.child("date").setValue(date)
            myRef.child("description").setValue(description)
            myRef.child("url").setValue(url)
           //myRef.child("link").setValue(link)

            finish()
        }
    }
}