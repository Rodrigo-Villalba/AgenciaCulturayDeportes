@file:Suppress("DEPRECATION")

package com.example.agenciaculturaydeportes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_administrador_add.*


class AdministradorAddActivity : AppCompatActivity() {

    /*private lateinit var button: mUploadBtn
    private lateinit var storageReference: mStorage*/
    private val database = Firebase.database
    //private val GALLERY_INTENT = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador_add)

        //val mStorage = FirebaseStorage.getInstance().getReference()

        //val mUploadBtn = (button) findViewById(R.id.botonSubir)


       // mUploadBtn.setOnClickListener(new View.OnClickListener() {

             //fun onClick(View v){
        // val intent = Intent(Intent.ACTION_PICK)
              //  intent.type = "image/*"
              //  startActivityForResult(intent, GALLERY_INTENT)

         //   }
      //  })



        val myRef = database.getReference("evento")


        val name=nameEditText.text
        val date=dateEditText.text
        val description=descriptionEditText.text
        val url=urlEditText.text


        saveButton.setOnClickListener {
            val evento = Evento(name.toString(), date.toString(), description.toString(), url.toString())
            myRef.child(myRef.push().key.toString()).setValue(evento)
            finish()
        }
    }
}