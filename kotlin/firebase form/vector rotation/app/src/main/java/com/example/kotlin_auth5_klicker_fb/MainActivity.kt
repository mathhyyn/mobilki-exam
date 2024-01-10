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

    fun rotateVector(originalVector: Vector3D, angleX: Double, angleY: Double, angleZ: Double): Vector3D {
        // Преобразуем углы в радианы
        val radX = Math.toRadians(angleX)
        val radY = Math.toRadians(angleY)
        val radZ = Math.toRadians(angleZ)

        // Матрицы поворота по осям X, Y и Z
        val rotationMatrixX = arrayOf(
            doubleArrayOf(1.0, 0.0, 0.0),
            doubleArrayOf(0.0, cos(radX), -sin(radX)),
            doubleArrayOf(0.0, sin(radX), cos(radX))
        )

        val rotationMatrixY = arrayOf(
            doubleArrayOf(cos(radY), 0.0, sin(radY)),
            doubleArrayOf(0.0, 1.0, 0.0),
            doubleArrayOf(-sin(radY), 0.0, cos(radY))
        )

        val rotationMatrixZ = arrayOf(
            doubleArrayOf(cos(radZ), -sin(radZ), 0.0),
            doubleArrayOf(sin(radZ), cos(radZ), 0.0),
            doubleArrayOf(0.0, 0.0, 1.0)
        )

        // Умножаем вектор на матрицы поворота
        val rotatedX = multiplyMatrixVector(rotationMatrixX, originalVector)
        val rotatedY = multiplyMatrixVector(rotationMatrixY, rotatedX)
        val rotatedZ = multiplyMatrixVector(rotationMatrixZ, rotatedY)
        return rotatedZ
    }

    fun multiplyMatrixVector(matrix: Array<DoubleArray>, vector: Vector3D): Vector3D {
        val result = DoubleArray(3)
        result[0] = matrix[0][0] * vector.x + matrix[0][1] * vector.y + matrix[0][2] * vector.z
        result[1] = matrix[1][0] * vector.x + matrix[1][1] * vector.y + matrix[1][2] * vector.z
        result[2] = matrix[2][0] * vector.x + matrix[2][1] * vector.y + matrix[2][2] * vector.z
        return Vector3D(result[0], result[1], result[2])
    }

    var fbAuth = FirebaseAuth.getInstance()
    lateinit var _db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        var et_x = findViewById(R.id.et_x) as EditText
        var et_y = findViewById(R.id.et_y) as EditText
        var et_z = findViewById(R.id.et_z) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button
        val tvResult: TextView = findViewById(R.id.et_text)

        val myRef = FirebaseDatabase.getInstance("https://lab5-mobile-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        var x_0 = 0.0
        var y_0 = 0.0
        var z_0 = 0.0
        var vec = Vector3D(x_0,y_0,z_0)
        myRef.child("x").get().addOnSuccessListener { dataSnapshot ->
            x_0 = dataSnapshot.getValue(Double::class.java)!!
        }
        myRef.child("y").get().addOnSuccessListener { dataSnapshot ->
            y_0 = dataSnapshot.getValue(Double::class.java)!!
        }
        myRef.child("z").get().addOnSuccessListener { dataSnapshot ->
            z_0 = dataSnapshot.getValue(Double::class.java)!!
            tvResult.text = "$x_0 $y_0 $z_0"
            vec = Vector3D(x_0,y_0,z_0)
        }


        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_x.setText("0")
            et_y.setText("0")
            et_z.setText("0")
        }

        var counter = 0
        btn_submit.setOnClickListener { view ->
            val a = et_x.text.toString().toDouble();
            val b = et_y.text.toString().toDouble();
            val c = et_z.text.toString().toDouble();

            vec = rotateVector(vec, a, b, c)
            tvResult.text = "${vec.x} ${vec.y} ${vec.z}"

            //signIn(view, user_name, password)

            val myRef =
                FirebaseDatabase.getInstance("https://lab5-mobile-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
            counter++
            myRef.child("counter").setValue(counter)
            myRef.child("x").setValue(vec.x)
            myRef.child("y").setValue(vec.y)
            myRef.child("z").setValue(vec.z)

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