package com.example.covidtest.modules


import com.google.gson.annotations.SerializedName

data class CurrentSummary(
    @SerializedName("Countries")
    val countries: List<Country>,
    @SerializedName("Date")
    val date: String,
    @SerializedName("Global")
    val global: Global
)