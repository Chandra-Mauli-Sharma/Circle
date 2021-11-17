package com.example.circle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.circle.Models.Message
import com.example.circle.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter(private val dataSet: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SENDER_VIEW_TYPE = 1
        const val RECEIVER_VIEW_TYPE = 2
    }

    class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var senderMsg: TextView
        var senderTime: TextView

        init {
            senderMsg = view.findViewById(R.id.senderText)
            senderTime = view.findViewById(R.id.senderTime)
        }
    }

    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var receiverMsg: TextView
        var receiverTime: TextView

        init {
            receiverMsg = view.findViewById(R.id.receiverText)
            receiverTime = view.findViewById(R.id.receiverTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            SENDER_VIEW_TYPE -> SenderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sender_layout, parent, false)
            )

            else -> ReceiverViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.receiver_layout, parent, false)
            )
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder.javaClass==SenderViewHolder::class) {
//            (holder as SenderViewHolder).senderMsg.text=dataSet[position].message
//        } else {
            (holder as ReceiverViewHolder).receiverMsg.text= dataSet[position].message
//        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int =
        if (dataSet[position].uid.equals(Firebase.auth.uid)) {
            SENDER_VIEW_TYPE
        } else {
            RECEIVER_VIEW_TYPE
        }

}