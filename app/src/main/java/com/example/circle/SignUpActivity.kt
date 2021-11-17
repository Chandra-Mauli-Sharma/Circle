package com.example.circle

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.circle.Models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var username:EditText
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email=findViewById<EditText>(R.id.editTextTextEmailAddress)
        password=findViewById(R.id.editTextTextPassword)
        username=findViewById(R.id.editTextTextPersonName)
        auth = Firebase.auth
        database = Firebase.database

    }

    public fun onClick(v: View) {
        auth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                Toast.makeText(
                    baseContext, "Authentication successful.",
                    Toast.LENGTH_SHORT
                ).show()

                var userClient = Users(
                    userName = username.text.toString(),
                    mail = email.text.toString(),
                    password = password.text.toString()
                )

                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser

                var id= user?.uid

                if (id != null) {
                    database.reference.child("Users").child(id).setValue(userClient)
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }

    fun AlreadyHaveAccount(v:View){
        startActivity(Intent(this,SignInActivity::class.java))
    }
}