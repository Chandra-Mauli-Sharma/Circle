package com.example.circle.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.circle.ChatDetailActivity
import com.example.circle.Models.Users
import com.example.circle.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersAdapter(private val dataSet: ArrayList<Users>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userName: TextView
        var lastMessage: TextView
        var profilePic: ImageView
        init {
            userName = view.findViewById(R.id.userName)
            lastMessage = view.findViewById(R.id.lastMessage)
            profilePic = view.findViewById(R.id.profile_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_display, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = dataSet[position]
        holder.userName.text = user.userName
        holder.lastMessage.text = user.lastMessage
        Firebase.database.reference.child("Chats").child(Firebase.auth.uid+user.userId)
            .orderByChild("timestamp")
            .limitToLast(1)
            .addListenerForSingleValueEvent( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.hasChildren())
                    {
                        for (dataSnapshot in snapshot.children) {
                            holder.lastMessage.text=dataSnapshot.child("message").value.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        if(user.profilepic!=null)
        {
            Glide.with(holder.itemView.context)
                .load(user.profilepic)
                .into(holder.profilePic)
        }else{
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_baseline_person_24)
                .into(holder.profilePic)
        }
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView.context, ChatDetailActivity::class.java)
            intent.putExtra("UserId", user.userId)
            intent.putExtra("UserName", user.userName)
            intent.putExtra("ProfilePhoto", user.profilepic)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = dataSet.size
}