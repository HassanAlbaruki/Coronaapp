package com.hassan.corona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tableLayout=findViewById(R.id.tabLayout) as TabLayout
        val viewpager=findViewById(R.id.viewpager) as ViewPager
        val adapter = TabAdapter(supportFragmentManager)
        val imgBack = findViewById(R.id.img_back) as ImageView
        imgBack.setOnClickListener {
            finish()
        }
        adapter.addFragment(Tab1Fragment(), "Germany")
        adapter.addFragment(Tab2Fragment(), "Global")
        adapter.addFragment(Tab3Fragment(), "List")
        viewpager.setAdapter(adapter)
        tableLayout.setupWithViewPager(viewpager)

    }
}
