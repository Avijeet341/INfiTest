package com.avi.infinitywalls

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.avi.infinitywalls.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpBinding: ActivitySignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)

        //stop from going into night mode
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //signUpBg()
        firebaseAuth = FirebaseAuth.getInstance()

        signUpBinding.buttonSignUp.setOnClickListener {
            val email = signUpBinding.editTextViewEmailSignUP.text.toString()
            val pass = signUpBinding.editTextViewPasswordSignUP.text.toString()
            val confirmPass = signUpBinding.editTextViewConfirmPasswordSignUP.text.toString()
            if (email.isNotEmpty() and pass.isNotEmpty() and confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching 😫.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!!😓.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun signUpWithFireBase() {

    }

    private fun signUpBg() {
        val constraintLayout: ConstraintLayout = findViewById(R.id.signUpLayout)
        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()
    }
}