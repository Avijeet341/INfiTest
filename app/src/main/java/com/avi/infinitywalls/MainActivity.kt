package com.avi.infinitywalls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.avi.infinitywalls.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpTabBar()
        mainBinding.buttonSignOut.setOnClickListener {
            signOut()

        }


    }

    private fun signOut() {
        //email and password signOut
        FirebaseAuth.getInstance().signOut()

        //google account sign out
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()

        val googleSignInClient= GoogleSignIn.getClient(this,gso)
        googleSignInClient.signOut().addOnCompleteListener {task->

            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Sign Out is successful✅💕👍", Toast.LENGTH_SHORT).show()
                val intent=Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }


    }

    private fun setUpTabBar() {
        mainBinding.bottomNavBar.setOnItemSelectedListener {
            //it represent item
            when (it) {
               // R.id.nav_home -> mainBinding.textMain.text = "Home."
               // R.id.nav_cat -> mainBinding.textMain.text = "Cat."

                R.id.nav_fire ->{
                    mainBinding.apply {
                       // textMain.text="Fire."
                        bottomNavBar.showBadge(R.id.nav_Profile)
                    }
                }
                R.id.nav_Profile->{
                    mainBinding.apply {
                       // textMain.text="Profile."
                        bottomNavBar.dismissBadge(R.id.nav_Profile)
                    }
                }

            }
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Perform the exit operation
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            // Dismiss the dialog if the user chooses not to exit
            dialog.dismiss()
        }
        builder.create().show()
    }
}