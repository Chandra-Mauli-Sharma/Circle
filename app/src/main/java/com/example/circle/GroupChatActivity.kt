package com.example.circle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.circle.Models.Message
import com.example.circle.adapter.ChatAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class GroupChatActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var nameText: TextView
    private lateinit var auth: FirebaseAuth
    lateinit var profilePicture: ImageView
    lateinit var chatRecyclerView: RecyclerView
    lateinit var senderId: String

    var message = ArrayList<Message>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        supportActionBar?.hide()
        auth = Firebase.auth
        senderId = auth.uid.toString()
        database=Firebase.database

        profilePicture = findViewById<ImageView>(R.id.profile_image2)
        profilePicture.setImageResource(R.drawable.ic_baseline_person_24)
        nameText = findViewById<TextView>(R.id.textView3).apply {
            text = "Random Chat"
        }

        chatRecyclerView = findViewById(R.id.recyclerView2)

        database.reference.child("Chats").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                message.clear()
                for (dataSnapshot in snapshot.children) {
                    val users = dataSnapshot.getValue(Message::class.java)

                    if (users != null) {
                        message.add(users)
                    }
                }
                chatRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    // set the custom adapter to the RecyclerView
                    adapter = ChatAdapter(message)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun onClick(v: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun sendMessage(v: View) {
        var editTextMessage = findViewById<EditText>(R.id.editTextMessage2)

        var model = Message(
            uid = senderId,
            message = editTextMessage.text.toString(),
            timeStamp = Date().time
        )
        editTextMessage.setText("")
        database.reference.child("GroupChats")
            .push()
            .setValue(model).addOnSuccessListener {
                OnSuccessListener<Void> {

                }
            }
    }
}