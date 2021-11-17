package com.example.circle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.circle.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
val tabArray= arrayOf(
    "Chat",
    "Meme",
    "Jokes"
)
class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2=findViewById(R.id.viewPager2)
        tabLayout=findViewById(R.id.tabLayout)
        val adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager2){
            tab,position->tab.text= tabArray[position]
        }.attach()
        auth=Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.LogOut -> {
                auth.signOut()
                startActivity(Intent(this,SignInActivity::class.java))
            }
            R.id.GroupChat->{
                startActivity(Intent(this,GroupChatActivity::class.java))
            }

        }
        return true
    }
}