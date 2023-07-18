package com.kotlinliza.combination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

        val loginTxt = findViewById<TextView>(R.id.log_txt)
        val signupBtn = findViewById<Button>(R.id.butt)
        val email = findViewById<TextInputEditText>(R.id.emailTxt)
        val password = findViewById<TextInputEditText>(R.id.passwordTxt)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val mainContainer = findViewById<LinearLayout>(R.id.main)

        loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signupBtn.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            mainContainer.visibility = View.GONE

            //getting the content in the above
            val emailString = email.text.toString()
            val passwordString = password.text.toString()

            if ( emailString.isNotEmpty() || passwordString.isNotEmpty() ) {
                auth.createUserWithEmailAndPassword(emailString,passwordString)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,LoginActivity::class.java))
                            Toast.makeText(baseContext, "login successful.", Toast.LENGTH_SHORT,).show()
                        } else {
                            progressBar.visibility = View.GONE
                            mainContainer.visibility = View.VISIBLE
                            Toast.makeText(baseContext, "Signup failed. Try again later", Toast.LENGTH_SHORT,).show()

                        }

                    }
            } else {
                progressBar.visibility = View.GONE
                mainContainer.visibility = View.VISIBLE
                Toast.makeText(baseContext, "Fill in both fields!", Toast.LENGTH_SHORT,).show()


            }

        }
    }
}