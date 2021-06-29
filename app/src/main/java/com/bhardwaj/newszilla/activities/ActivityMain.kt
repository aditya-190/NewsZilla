package com.bhardwaj.newszilla.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.adapters.MainPageFragmentAdapter
import com.bhardwaj.newszilla.fragments.DiscoverFragment
import com.bhardwaj.newszilla.fragments.HomeFragment
import com.google.android.material.snackbar.Snackbar

class ActivityMain : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var mainPageAdapter: MainPageFragmentAdapter

    companion object {
        lateinit var vpActivityMain: ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialise()
    }

    private fun initialise() {
        mContext = this@ActivityMain
        vpActivityMain = findViewById(R.id.vpActivityMain)
        mainPageAdapter = MainPageFragmentAdapter(supportFragmentManager, lifecycle)
        mainPageAdapter.addFragment(DiscoverFragment().newInstance())
        mainPageAdapter.addFragment(HomeFragment().newInstance())
        vpActivityMain.adapter = mainPageAdapter
        vpActivityMain.currentItem = 1
        vpActivityMain.isUserInputEnabled = false
    }

    override fun onBackPressed() {
        if (vpActivityMain.currentItem == 1) {
            Snackbar.make(vpActivityMain, "Are you sure you want to exit?", Snackbar.LENGTH_SHORT)
                .also {
                    it.setAction("Yes") { finishAffinity() }
                    it.setActionTextColor(
                        resources.getColor(
                            R.color.textColorWhite,
                            mContext.theme
                        )
                    )
                }.show()
        } else {
            vpActivityMain.currentItem = 1
        }
    }
}
