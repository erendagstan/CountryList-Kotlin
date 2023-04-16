package com.erendagistan.kotlincountrylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erendagistan.kotlincountrylist.R
import com.erendagistan.kotlincountrylist.databinding.FragmentCountryBinding
import com.erendagistan.kotlincountrylist.util.downloadFromUrl
import com.erendagistan.kotlincountrylist.util.placeHolderProgressBar
import com.erendagistan.kotlincountrylist.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.item_country.*
import kotlinx.android.synthetic.main.item_country.countryName
import kotlinx.android.synthetic.main.item_country.countryRegion

class CountryFragment : Fragment() {
    private var countryUuid=0
    private lateinit var countryViewModel : CountryViewModel
    private lateinit var dataBinding : FragmentCountryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
        //return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid= CountryFragmentArgs.fromBundle(it).countryUuid
        }
        countryViewModel=ViewModelProvider(this).get(CountryViewModel::class.java)
        countryViewModel.getDataFromRoom(countryUuid)

        observeLiveData()


    }

    private fun observeLiveData(){
        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                dataBinding.selectedCountry= it  //country
  /*              countryName.text=it.countryName
                countryRegion.text=it.countryRegion
                countryLanguage.text=it.countryLanguage
                countryCapital.text=it.countryCapital
                countryCurrency.text=it.countryCurrency
                context?.let {context->
                    countryImage.downloadFromUrl(it.imageUrl, placeHolderProgressBar(context))
                }*/
            }
        })
    }

}