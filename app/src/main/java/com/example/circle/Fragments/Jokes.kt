package com.example.circle.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.circle.Models.Memes
import com.example.circle.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Jokes : Fragment() {

    lateinit var jokeText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_jokes, container, false)
        jokeText = view.findViewById<TextView>(R.id.jokesText)
        var refreshButton=view.findViewById<FloatingActionButton>(R.id.refreshButton2)

        refresh()
        refreshButton.setOnClickListener{
            refresh()
        }
        return view
    }

    private fun refresh() {
        val url = "https://v2.jokeapi.dev/joke/Programming"

        var jsonResponse = ArrayList<Memes>()
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.getString("type") == "single") {
                    jokeText.text = response.getString("joke")
                } else {
                    val joke = "Question\n"+response.getString("setup") + "\nAnswer\n" + response.getString("delivery")
                    jokeText.text = joke
                }
            },
            { error ->
                error.printStackTrace()
            }
        )

        queue.add(jsonObjectRequest)

    }
}