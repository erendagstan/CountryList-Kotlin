package com.erendagistan.kotlincountrylist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.erendagistan.kotlincountrylist.R
import com.erendagistan.kotlincountrylist.databinding.ItemCountryBinding
import com.erendagistan.kotlincountrylist.model.Country
import com.erendagistan.kotlincountrylist.util.downloadFromUrl
import com.erendagistan.kotlincountrylist.util.placeHolderProgressBar
import com.erendagistan.kotlincountrylist.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() , CountryClickListener{

    class CountryViewHolder(val view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //item country layout ile adapter bağlama
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_country,parent,false)

        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return countryList.count()
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country= countryList[position]

        holder.view.listener=this

//        holder.view.countryName.text=countryList[position].countryName
//        holder.view.countryRegion.text=countryList[position].countryRegion
//
//        holder.view.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl,
//            placeHolderProgressBar(holder.view.context)
//        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}