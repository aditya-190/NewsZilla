package com.bhardwaj.newszilla.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityOptionsCompat
import com.bhardwaj.newszilla.R

class ActivitySplashScreen : AppCompatActivity() {

    private lateinit var motionLayout: MotionLayout
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.navigationBarColor = resources.getColor(R.color.colorBackground, this.theme)
        setContentView(R.layout.activity_splash_screen)
        initialise()
        clickListeners()
    }

    private fun initialise() {
        mContext = this@ActivitySplashScreen
        motionLayout = findViewById(R.id.splashScreen)
    }

    private fun clickListeners() {
        motionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                val intent = Intent(mContext, ActivityMain::class.java)
                val animation =
                    ActivityOptionsCompat.makeCustomAnimation(
                        mContext,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                startActivity(intent, animation.toBundle())
                finish()
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        }
        )
    }

    override fun onResume() {
        super.onResume()
        motionLayout.startLayoutAnimation()
    }
}