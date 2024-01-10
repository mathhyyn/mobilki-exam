package com.example.kotlin_auth5_klicker_fb

import android.content.Intent
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.*

data class Vector3D(val x: Double, val y: Double, val z: Double)


class MainActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    lateinit var _db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        var et_login = findViewById(R.id.et_x) as EditText
        var et_pass = findViewById(R.id.et_y) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button

        // ссылка на firebase + не забыть про connect (Tools/Firebase)
        val myRef = FirebaseDatabase.getInstance("https://lab5-mobile-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        var login = ""
        var pass = ""

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_login.setText("")
            et_pass.setText("")
        }

        // var counter = 0
        btn_submit.setOnClickListener { view ->
            val user_name = et_login.text.toString();
            val password = et_pass.text.toString();

            signIn(view, user_name, password)

            // пример изменения переменной
            // myRef.child("counter").setValue(counter)

        }
    }

    fun signIn(view: View, email: String, password: String) {

        val myToast =
            Toast.makeText(this, "email=" + email + " pas=" + password, Toast.LENGTH_SHORT)
        myToast.show()


        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {

                    val myToast = Toast.makeText(this, "done!", Toast.LENGTH_SHORT)
                    myToast.show()

                } else {
                    val myToast = Toast.makeText(this, "failed :-(", Toast.LENGTH_SHORT)
                    myToast.show()
                }
            })

    }
}