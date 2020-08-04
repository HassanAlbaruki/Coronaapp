package com.hassan.corona

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hassan.corona.adapters.IntroViewPagerAdapter
import com.hassan.corona.models.ScreenItem


class IntroActivity : AppCompatActivity() {

    lateinit var screenPager: ViewPager
    lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    lateinit var tabIndicator: TabLayout
    lateinit var btnNext: Button
    var position = 0
    lateinit var btnGetStarted: Button
    lateinit var btnAnim: Animation
    lateinit var tvSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN )

        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
        setContentView(R.layout.activity_intro);

        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // fill list screen
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Wash your Hands",
                " Wash your hands regularly with soap and water or alcohol ",
                R.drawable.page1
            )
        )
        mList.add(
            ScreenItem(
                "Ware a mask ",
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.page3
            )
        )
        mList.add(
            ScreenItem(
                "Avoid touching\nyour face ",
                " avoid touching your nose,eyes and mouth with\nunclean hands",
                R.drawable.page2
            )
        )

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter =
            IntroViewPagerAdapter(this, mList)
        screenPager.setAdapter(introViewPagerAdapter)

        // setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager)

        // next button click Listner
        btnNext.setOnClickListener(){
            position = screenPager.currentItem
            if (position < mList.size) {
                position++
                screenPager.currentItem = position
            }
            if (position == mList.size - 1) { // when we rech to the last screen
                loaddLastScreen()
            }
        }

        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
            }

        })

        // Get Started button click listener
        btnGetStarted.setOnClickListener() {

                //open main activity
                val mainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainActivity)
                //savePrefsData()
                finish()
        }

        tvSkip.setOnClickListener() {
                screenPager.currentItem = mList.size
        }

    }
    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE )
        return pref.getBoolean("isIntroOpnend", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE   )
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private fun loaddLastScreen() {
        btnNext.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        tvSkip.visibility = View.INVISIBLE
        tabIndicator.visibility = View.INVISIBLE
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.animation = btnAnim
    }
}
