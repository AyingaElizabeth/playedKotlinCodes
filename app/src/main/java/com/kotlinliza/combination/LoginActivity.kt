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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
           startActivity( Intent(  this@LoginActivity, MainActivity::class.java) )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance()

        val signupTxt=findViewById<TextView>(R.id.sign_txt)
        val loginBtn=findViewById<Button>(R.id.butt)
        val email = findViewById<TextInputEditText>(R.id.emailTxt)
        val password = findViewById<TextInputEditText>(R.id.passwordTxt)
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        val mainContainer=findViewById<LinearLayout>(R.id.main)



        signupTxt.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        loginBtn.setOnClickListener {
            //getting the content in the above
            val emailString = email.text.toString()
            val passwordString = password.text.toString()
            progressBar.visibility= View.VISIBLE
            mainContainer.visibility= View.GONE
            if(emailString.isNotEmpty()||passwordString.isNotEmpty()){
                auth.signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(
                                baseContext,
                                "signUp successful.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Check your email or password",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }

                    }

            }else{
                progressBar.visibility=View.GONE
                mainContainer.visibility=View.VISIBLE
                Toast.makeText(baseContext,"please fill in all the fields",Toast.LENGTH_SHORT).show()
            }

    }
}}