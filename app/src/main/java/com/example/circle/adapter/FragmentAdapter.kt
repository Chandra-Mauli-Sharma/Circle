package com.example.circle.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.circle.Fragments.Chat
import com.example.circle.Fragments.Jokes
import com.example.circle.Fragments.Meme

private const val NUM_TABS = 3

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Chat()
            1-> return Meme()

        }
        return Jokes()
    }

}