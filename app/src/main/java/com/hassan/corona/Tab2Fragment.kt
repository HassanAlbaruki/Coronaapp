package com.hassan.corona

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.covidtest.modules.CurrentSummary


import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.hassan.corona.R
import kotlinx.android.synthetic.main.fragment_tab1.*
import java.text.NumberFormat
import java.util.*

import kotlin.collections.ArrayList

class Tab2Fragment : Fragment() {

    lateinit var totalConfermed: TextView
    lateinit var totalDeth: TextView
    lateinit var totalRecoverd: TextView
    lateinit var newCases: TextView
    lateinit var newDeths: TextView
    lateinit var effly: RelativeLayout
    lateinit var dethly: RelativeLayout
    lateinit var recovly: RelativeLayout
    lateinit var newcaly: RelativeLayout
    lateinit var newdethly: RelativeLayout
    lateinit var itemsswipetorefresh: SwipeRefreshLayout
    lateinit var no_connection: LinearLayout
    lateinit var retry: Button
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view: View = inflater.inflate( R.layout.fragment_tab2, container,
            false)
        val barchart=view.findViewById(R.id.chart) as BarChart
        val NoOfEmp = ArrayList<BarEntry>()
        barchart.setDrawGridBackground(false)

        effly = view.findViewById(R.id.affected_gl_ly) as RelativeLayout
        dethly = view.findViewById(R.id.deth_gl_ly) as RelativeLayout
        recovly = view.findViewById(R.id.rec_gl_ly) as RelativeLayout
        newcaly = view.findViewById(R.id.new_ca_gl_ly) as RelativeLayout
        newdethly = view.findViewById(R.id.new_deth_gl_ly) as RelativeLayout

        totalConfermed = view.findViewById(R.id.affected_global) as TextView
        totalDeth = view.findViewById(R.id.deth_global) as TextView
        totalRecoverd = view.findViewById(R.id.recoverd_global) as TextView
        newCases = view.findViewById(R.id.newCases) as TextView
        newDeths = view.findViewById(R.id.newDeth) as TextView
        itemsswipetorefresh =view.findViewById(R.id.swipe_container) as SwipeRefreshLayout
        no_connection=view.findViewById(R.id.ly_no_connection)

        itemsswipetorefresh.setOnRefreshListener {
            loadData()
            itemsswipetorefresh.isRefreshing = false
        }
        retry=view.findViewById(R.id.retry) as Button
        retry.setOnClickListener {
            loadData()
        }

        loadData()
        NoOfEmp.add(BarEntry(5000f, 0))
        NoOfEmp.add(BarEntry(10000f, 1))
        NoOfEmp.add(BarEntry(15000f, 2))
        NoOfEmp.add(BarEntry(20000f, 3))
        NoOfEmp.add(BarEntry(25000f, 4))

        val year=ArrayList<String>()

        year.add("Jan")
        year.add("Feb")
        year.add("Mar")
        year.add("Apr")
        year.add("May")


        val bardataset = BarDataSet(NoOfEmp, "No Of Cases")
        barchart.animateY(5000)
        val data = BarData(year, bardataset)
        bardataset.setColor(Color.rgb(255, 89, 89))
        bardataset.setBarSpacePercent(70f);
        barchart.setDrawGridBackground(false)
        barchart.setData(data)





        return view

    }
fun loadData(){

    val stb= AnimationUtils.loadAnimation(activity,R.anim.stb)
    val stb1= AnimationUtils.loadAnimation(activity,R.anim.stb1)
    val stb2= AnimationUtils.loadAnimation(activity,R.anim.stb2)
    val stb3= AnimationUtils.loadAnimation(activity,R.anim.stb3)
    val stb4= AnimationUtils.loadAnimation(activity,R.anim.stb4)
    effly.startAnimation(stb)
    dethly.startAnimation(stb1)
    recovly.startAnimation(stb2)
    newcaly.startAnimation(stb3)
    newdethly.startAnimation(stb4)
    AndroidNetworking.initialize(activity)
    AndroidNetworking.get("https://api.covid19api.com/summary")
        .build()
        .getAsObject(CurrentSummary::class.java, object : ParsedRequestListener<CurrentSummary>
        {
            override fun onResponse(response: CurrentSummary) {
                no_connection.visibility = View.GONE
                swipe_container.visibility=View.VISIBLE
                val global = response.global
                totalConfermed.text= NumberFormat.getNumberInstance(Locale.US).format(global.totalConfirmed)
                totalDeth.text=NumberFormat.getNumberInstance(Locale.US).format(global.totalDeaths)
                totalRecoverd.text=NumberFormat.getNumberInstance(Locale.US).format(global.totalRecovered)
                newCases.text=NumberFormat.getNumberInstance(Locale.US).format(global.newConfirmed)
                newDeths.text=NumberFormat.getNumberInstance(Locale.US).format(global.newDeaths)

            }

            override fun onError(anError: ANError?) {
                no_connection.visibility = View.VISIBLE
                swipe_container.visibility=View.GONE
                Log.d("Error fr2 ",anError.toString())
            }
        }


        )
}


}