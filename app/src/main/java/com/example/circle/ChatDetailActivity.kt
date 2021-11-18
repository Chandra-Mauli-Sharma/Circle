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
import com.bumptech.glide.Glide
import com.example.circle.Models.Message
import com.example.circle.adapter.ChatAdapter
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
import android.widget.Toast
import com.google.firebase.Timestamp
import java.text.DateFormat


class ChatDetailActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var nameText: TextView
    lateinit var profilePicture: ImageView
    lateinit var chatRecyclerView: RecyclerView
    private lateinit var senderId: String
    lateinit var receiverId: String
    lateinit var senderRoom: String
    lateinit var receiverRoom: String
    var message = ArrayList<Message>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        auth = Firebase.auth
        database = Firebase.database

        supportActionBar?.hide()

        senderId = auth.uid.toString()
        receiverId = intent.getStringExtra("UserId").toString()
        val userName = intent.getStringExtra("UserName")
        val profilePic = intent.getStringExtra("ProfilePhoto")
        senderRoom = senderId + receiverId
        receiverRoom = receiverId + senderId

        profilePicture = findViewById<ImageView>(R.id.profile_image)
        profilePicture.setImageResource(R.drawable.ic_baseline_person_24)
        nameText = findViewById<TextView>(R.id.textView2).apply {
            text = userName
        }


        Glide.with(this)
            .load(profilePic)
            .into(profilePicture)

        chatRecyclerView = findViewById(R.id.recyclerView)


        database.reference.child("Chats").child(senderRoom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    message.clear()
                    for (dataSnapshot in snapshot.children) {
                        val messages: Message? = dataSnapshot.getValue(Message::class.java)

                        if (messages != null) {
                            message.add(messages)
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
        var editTextMessage = findViewById<EditText>(R.id.editTextMessage)

        val timestamp = Timestamp.now()
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = DateFormat.getTimeInstance(DateFormat.SHORT)
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()

        var model = Message(
            uid = senderId,
            message = editTextMessage.text.toString(),
            timeStamp = date
        )
        editTextMessage.setText("")

        database.reference.child("Chats")
            .child(senderRoom)
            .push()
            .setValue(model
            ) { databaseError, databaseReference ->
                if (databaseError != null) {
                    Toast.makeText(
                        this,
                        "Some error occured, try again",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    database.reference.child("Chats").child(receiverRoom).push().setValue(model)
                }
            }

    }
}