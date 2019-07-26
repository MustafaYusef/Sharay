package com.mustafayusef.sharay.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.mustafayusef.sharay.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class splashScreen : AppCompatActivity() {
    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        imageSplash.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        Handler().postDelayed({
            imageSplash.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                imageSplash.visibility = View.GONE
                val intent= Intent(this,Lottie::class.java)
                startActivity(intent)
                finish()
            },100)
        },2500)


    }
}
