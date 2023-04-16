package com.erendagistan.kotlincountrylist.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erendagistan.kotlincountrylist.model.Country
import com.erendagistan.kotlincountrylist.service.CountryAPIService
import com.erendagistan.kotlincountrylist.service.CountryDatabase
import com.erendagistan.kotlincountrylist.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) :BaseViewModel(application) {
    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferences =CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customSharedPreferences.getTime()

        if(updateTime!=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){ // eğer 10 dkdan küçükse geçen zaman
            getDataFromSQLite()
        } else{
            getDataFromAPI()
        }

    }

    fun refreshDataFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoading.value=true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Get data from SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Get data from API",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries (countryList: List<Country>){
        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }

    private fun storeInSQLite(list: List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())  //list -> individual
            var i=0
            while(i<list.size){
                list[i].uuid=listLong[i].toInt()
                i++
            }

            showCountries(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()  //hafızayı verimli hale getirmek için
    }


}