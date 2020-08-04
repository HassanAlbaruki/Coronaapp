package com.hassan.corona.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.hassan.corona.R
import com.hassan.corona.models.ScreenItem


class IntroViewPagerAdapter(private var mContext: Context,private var mListScreen: List<ScreenItem>): PagerAdapter() {


    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view==o
    }

    override fun getCount(): Int {
        return mListScreen.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen: View = inflater.inflate(R.layout.layout_screen, null)

        val imgSlide: ImageView = layoutScreen.findViewById(R.id.intro_img)
        val title = layoutScreen.findViewById<TextView>(R.id.intro_title)
        val description = layoutScreen.findViewById<TextView>(R.id.intro_description)

        title.text = mListScreen[position].Title
        description.text = mListScreen[position].Description
        imgSlide.setImageResource(mListScreen[position].ScreenImg)

        container.addView(layoutScreen)

        return layoutScreen    }

}