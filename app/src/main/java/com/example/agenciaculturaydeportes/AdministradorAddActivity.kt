@file:Suppress("DEPRECATION")

package com.example.agenciaculturaydeportes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_administrador_add.*


class AdministradorAddActivity : AppCompatActivity() {


    private val fileResult = 1
    private val database = Firebase.database
    private val myRef = database.getReference("evento")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador_add)


        val name = nameEditText.text
        val date = dateEditText.text
        val description = descriptionEditText.text
        val url = urlEditText.text


        uploadImageView.setOnClickListener {
            fileManager()
        }


        saveButton.setOnClickListener {
            val evento = Evento(
                name.toString(),
                date.toString(),
                description.toString(),
                url.toString()
            )
            myRef.child(myRef.push().key.toString()).setValue(evento)
            finish()
        }//cierra el saveButton

    }//cierra el onCreate

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        intent.type = "*/*"
        startActivityForResult(intent, fileResult)
    }//cierra el fileManager



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                }else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }

            }
        }
    }//cierra el onActivityResult



    private fun fileUpload(mUri: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Imagen_Evento")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)

                myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
        }
    }//Cierra el fileUpload


}//cierra el AdministradorAddActivity







