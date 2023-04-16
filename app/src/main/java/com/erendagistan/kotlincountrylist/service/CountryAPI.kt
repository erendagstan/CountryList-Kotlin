package com.erendagistan.kotlincountrylist.service

import com.erendagistan.kotlincountrylist.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //GET - POST
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //ex : atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //base url : "https://raw.githubusercontent.com/"

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<Country>>
}