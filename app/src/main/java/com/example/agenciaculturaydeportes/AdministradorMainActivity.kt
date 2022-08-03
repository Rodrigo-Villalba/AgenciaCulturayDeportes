package com.example.agenciaculturaydeportes


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_administrador_main.*
import kotlinx.android.synthetic.main.evento_content.view.*


class AdministradorMainActivity : AppCompatActivity() {

    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private val listEventoActivities:MutableList<Evento> = ArrayList()
    val myRef = database.getReference("evento")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador_main)

        newFloatingActionButton.setOnClickListener { v ->
            val intent = Intent(this, AdministradorAddActivity::class.java)
            v.context.startActivity(intent)
        }

        listEventoActivities.clear()
        setupRecyclerView(eventoRecyclerView)

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listEventoActivities.clear()
                dataSnapshot.children.forEach { child ->
                    val evento: Evento? =
                        Evento(child.child("name").getValue<String>(),
                               child.child("date").getValue<String>(),
                               child.child("description").getValue<String>(),
                               child.child("url").getValue<String>(),
                               //child.child("link").getValue<String>(),
                               child.key)
                    evento?.let { listEventoActivities.add(it) }
                }
                recyclerView.adapter = EventoViewAdapter(listEventoActivities)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class EventoViewAdapter(private val values: List<Evento> ) :
        RecyclerView.Adapter<EventoViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.evento_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val evento = values[position]

            holder.mNameTextView.text = evento.name
            holder.mDateTextView.text = evento.date
            holder.mPosterImgeView?.let {

                Glide
                    .with(holder.itemView.context)
                    .load(evento.url)
                    .placeholder(R.drawable.logoagencia)
                    .into(it)


            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, EventosDetailActivity::class.java).apply {
                    putExtra("key", evento.key)
                }
                v.context.startActivity(intent)
            }

            holder.itemView.setOnLongClickListener { v ->
                val intent = Intent(v.context, AdministradorEditActivity::class.java).apply {
                    putExtra("key", evento.key)
                }
                v.context.startActivity(intent)
                true
            }

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.nameTextView
            val mDateTextView: TextView = view.dateTextView
            val mPosterImgeView: ImageView? = view.posterImgeView

        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listEventoActivities[viewHolder.adapterPosition].key?.let { myRef.child(it).setValue(null) }
                listEventoActivities.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}