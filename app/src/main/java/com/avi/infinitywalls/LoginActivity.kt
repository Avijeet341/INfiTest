package com.avi.infinitywalls

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.avi.infinitywalls.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        //cursor



        firebaseAuth=FirebaseAuth.getInstance()
        registerActivityForGoogleSignIn()


        animationLoginBg()

        loginBinding.apply {
            buttonLogin.setOnClickListener {
                val email = editTextTextEmailAddress.text.toString()
                val pass = editTextTextPassword.text.toString()

                signInUser(email,pass)

            }
            buttonGoogle.setOnClickListener {
                 signInGoogle()
            }

            buttonFacebook.setOnClickListener {

            }
            SignUptextView.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }

            forgetMyPasstextView.setOnClickListener {
                   val intent=Intent(applicationContext,ForgotActivity::class.java)
                    startActivity(intent)
            }

        }


    }



    override fun onStart() {
        super.onStart()
        val user=firebaseAuth.currentUser
        //if user is a member already or signed before user value will not be null
        if(user!=null){
            //this process take place at the same time when user enter the email and password and remember it until user again open the app
            Toast.makeText(applicationContext,"Welcome Guys😎",Toast.LENGTH_SHORT).show()
            val intent=Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private  fun signInUser(email:String,pass:String){

        if (email.isNotEmpty() and pass.isNotEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        it.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
        else {
            Toast.makeText(
                this@LoginActivity,
                "Empty Fields Are Not Allowed!!😓.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signInGoogle() {

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("355548059361-hlikq9d1e0pet2e16om91u1r2sq5ji21.apps.googleusercontent.com")
            .requestEmail().build()

        googleSignInClient=GoogleSignIn.getClient(this,gso)
        signIn()

    }

    private fun signIn() {
        val signInIntent:Intent=googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    private fun registerActivityForGoogleSignIn(){
        activityResultLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result->
                // captured the result entered by the user in google signIn page

                val resultCode=result.resultCode
                val data=result.data

                if(resultCode== RESULT_OK && data!=null){
                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

                    firebaseSignInWithGoogle(task)
                }

            })
    }

    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>) {

        try {
            val account: GoogleSignInAccount =task.getResult(ApiException::class.java)
            Toast.makeText(applicationContext,"Welcome Guys 💕🎇🎉🎊",Toast.LENGTH_SHORT).show()
            //now sigin is done using google btn close this page and open main act using intent
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            firebaseGoogleAccount(account)
        }
        catch (e: ApiException){
            Toast.makeText(applicationContext,e.localizedMessage?.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    private fun firebaseGoogleAccount(account: GoogleSignInAccount) {

        //this will help us find out which device is logged in specially idToken and we registered the authenticated device of the person logged into the system using idtoken
        val authCredential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener {task->

            if(task.isSuccessful){
                //retrive the user data if login is successful
//              val user=auth.currentUser

            }else{

            }

        }

    }


    private fun animationLoginBg() {
        val constraintLayout: ConstraintLayout = findViewById(R.id.LoginBgLayout)
        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(1000)
        animationDrawable.setExitFadeDuration(2000)
        animationDrawable.start()
    }
}