package com.mustafayusef.sharay.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

import kotlinx.android.synthetic.main.activity_main.*

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.KotprefModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.auth.signup.Login


class MainActivity : AppCompatActivity() {
    object cacheObj : KotprefModel() {

          var language by stringPref("")
        var Email by stringPref("")
        var phone by stringPref("")


        var Secondphone by stringPref("")
        var name by stringPref("")
        var id by intPref(0)
        var token by stringPref("")


        var phoneLogin by stringPref("")
        var PasswordLogin by stringPref("")

    }

   private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mustafayusef.sharay.R.layout.activity_main)
        Kotpref.init(this)
        if(cacheObj.language!=""){
            this?.let { it1 -> LocaleHelper.setLocale(it1,cacheObj.language ) }
        }
        navController= Navigation.findNavController(this, com.mustafayusef.sharay.R.id.navHost)
       bottomNav.setupWithNavController(navController)
//        NavigationUI.setupActionBarWithNavController(this,navController)
        searchBtn.setOnClickListener {
            //val view:View?=null
            navController?.navigate(R.id.searchFragment)

            val navBar = this?.findViewById<BottomNavigationView> (R.id.bottomNav)
            val toolbar = this?.findViewById<Toolbar> (R.id.ToolBar)

            navController?.addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.searchFragment) {
                    navBar?.visibility = View.GONE
                    toolbar?.visibility = View.GONE
                } else {
                    navBar?.visibility = View.VISIBLE
                    toolbar?.visibility = View.VISIBLE
                }

            }
        }
        filterBtn.setOnClickListener {
            //val view:View?=null
            navController?.navigate(R.id.filterFragment)

//            val navBar = this?.findViewById<BottomNavigationView> (R.id.bottomNav)
//            val toolbar = this?.findViewById<Toolbar> (R.id.ToolBar)
//
//            navController?.addOnDestinationChangedListener { _, destination, _ ->
//                if(destination.id == R.id.filterFragment) {
//                    navBar?.visibility = View.GONE
//                    toolbar?.visibility = View.GONE
//                } else {
//                    navBar?.visibility = View.VISIBLE
//                    toolbar?.visibility = View.VISIBLE
//                }
//
//            }
        }
      //  searchBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.search_fragment, null))


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }


}
