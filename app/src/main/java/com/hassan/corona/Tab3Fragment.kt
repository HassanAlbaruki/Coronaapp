package com.hassan.corona

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.covidtest.modules.Country
import com.example.covidtest.modules.CurrentSummary
import com.hassan.corona.adapters.CountryListAdapter
import kotlinx.android.synthetic.main.fragment_tab1.*


class Tab3Fragment : Fragment() {
    lateinit var country_list: ArrayList<Country>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CountryListAdapter
    lateinit var no_connection: LinearLayout
    lateinit var itemsswipetorefresh: SwipeRefreshLayout

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view: View = inflater.inflate(R.layout.fragment_tab3, container,false)
        no_connection=view.findViewById(R.id.ly_no_connection)
        recyclerView = view.findViewById(R.id.recycler_view)
        itemsswipetorefresh=view.findViewById(R.id.swipe_container)
        itemsswipetorefresh.setOnRefreshListener {
            loadData()
            itemsswipetorefresh.isRefreshing = false
        }
        country_list=ArrayList()

        loadData()

        return view

    }

    fun loadData(){
        AndroidNetworking.initialize(activity)
        AndroidNetworking.get("https://api.covid19api.com/summary")
            .build()
            .getAsObject(CurrentSummary::class.java, object : ParsedRequestListener<CurrentSummary>
            {
                override fun onResponse(response: CurrentSummary) {
                    no_connection.visibility = View.GONE
                    swipe_container.visibility=View.VISIBLE
                    val countries = response.countries

                    country_list.addAll(countries)
                    recyclerView.apply {
                        layoutManager = GridLayoutManager(activity, 1)
                        // set the custom adapter to the RecyclerView
                        adapter = CountryListAdapter(country_list)

                    }
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
