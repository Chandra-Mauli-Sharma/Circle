package com.example.circle.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.circle.adapter.MemeAdapter
import com.example.circle.Models.Memes
import com.example.circle.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Meme : Fragment() {

    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_meme, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view2)
        var refreshButton=view.findViewById<FloatingActionButton>(R.id.refreshButton)

        refresh()
        refreshButton.setOnClickListener{
            refresh()
        }
        return view
    }

    private fun refresh(){
        val url = "https://meme-api.herokuapp.com/gimme/10"
        var jsonResponse = ArrayList<Memes>()
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val memeJSONArray = response.getJSONArray("memes")
                for (i in 0 until memeJSONArray.length()) {
                    var memeResponse = memeJSONArray.getJSONObject(i)
                    var image = memeResponse.getString("url")
                    var text = memeResponse.getString("title")
                    jsonResponse.add(Memes(title = text, image = image))
                    recyclerView?.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(context)
                        // set the custom adapter to the RecyclerView
                        adapter = MemeAdapter(jsonResponse)
                    }
                }


            },
            { error ->
                error.printStackTrace()
            }
        )

        queue.add(jsonObjectRequest)
    }
}
