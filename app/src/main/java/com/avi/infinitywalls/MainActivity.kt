package com.avi.infinitywalls

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.avi.infinitywalls.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

       // background()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController
         bottomNavigation = findViewById(R.id.bottom_nav_bar)

        bottomNavigation.setMenuResource(R.menu.nav_menu) // Set the menu resource
         setUpTabBar()


        onBackPressedAvi()

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        mainBinding.buttonSignOut.setOnClickListener {
            signOut()

        }


    }

    private fun onBackPressedAvi() {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button press here
                if (navController.currentDestination?.id != R.id.homeFragment) {
                    // If not on the home fragment, navigate back to the home fragment
                    navController.navigate(R.id.homeFragment)
                    bottomNavigation.setItemSelected(R.id.nav_home, true) // Update selected item
                } else {
                    // If already on the home fragment, exit the app or perform other actions
                    showExitConfirmationDialog()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

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

        bottomNavigation.setOnItemSelectedListener { id ->
            // Handle item selection here
            when (id) {
                R.id.nav_home -> {
                    // Navigate to the corresponding destination
                    navController.navigate(R.id.homeFragment)
                }
                R.id.nav_cat-> {
                    // Navigate to the corresponding destination
                    navController.navigate(R.id.categoriesFragment)
                }

                R.id.nav_search->{
                    navController.navigate(R.id.searchFragment)
                }
                R.id.nav_fire->{
                    mainBinding.apply {
                        // textMain.text="Fire."
                        bottomNavBar.showBadge(R.id.nav_Profile)
                    }
                    navController.navigate(R.id.trendingFragment)
                }
                R.id.nav_Profile->{
                    mainBinding.apply {
                        // textMain.text="Profile."
                        bottomNavBar.dismissBadge(R.id.nav_Profile)
                    }
                    navController.navigate(R.id.profileFragment)
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
//    private fun background(){
//        val constraintLayout: ConstraintLayout = findViewById(R.id.LoginBgLayout)
//        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
//        animationDrawable.setEnterFadeDuration(2500)
//        animationDrawable.setExitFadeDuration(3000)
//        animationDrawable.start()
//    }
}