package com.hassan.corona.adapters


import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtest.modules.Country
import com.hassan.corona.Consts
import com.hassan.corona.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.countries_list_item.view.*


class CountryListAdapter(private var countryList: ArrayList<Country>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mcontext: Context

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<Country>) {
        this.countryList.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Country? {
        return countryList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Consts.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_list_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(mcontext).inflate(R.layout.loading_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (countryList[position] == null) {
            Consts.VIEW_TYPE_LOADING
        } else {
            Consts.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Consts.VIEW_TYPE_ITEM) {
            // holder.itemView.ly_background.setBackgroundColor(getColor())

            holder.itemView.country_name.text= countryList[position]!!.country
            holder.itemView.total_effect.text= countryList[position]!!.totalConfirmed.toString()
            holder.itemView.counter.text= (position+1).toString()+". "

        }
    }
}