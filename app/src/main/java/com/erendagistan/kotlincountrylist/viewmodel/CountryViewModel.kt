package com.erendagistan.kotlincountrylist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erendagistan.kotlincountrylist.model.Country
import com.erendagistan.kotlincountrylist.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value=country
        }

//        val country = Country("Turkey","Asia","Ankara","TRY","Turkish","ww.ss.com")
//        countryLiveData.value=country
    }

}