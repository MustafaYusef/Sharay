package com.mustafayusef.sharay.ui.search




import kotlinx.android.synthetic.main.car_card.view.*



import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.R
import android.view.View.OnClickListener
import com.bumptech.glide.request.RequestOptions
import com.mustafayusef.sharay.database.entitis.latestCar
import kotlinx.android.synthetic.main.last_search_card.view.*
import kotlinx.android.synthetic.main.sections_cards.view.*


class lastSearchAdapter(val context:Context, var onNoteLisener: OnLastLisener,val searchResult: List<latestCar>) : RecyclerView.Adapter<lastSearchAdapter.CustomViewHolder>(){
    //
    //var displayList=list
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.last_search_card ,parent,false)

        return  CustomViewHolder(cardItem,mOnNotlesener)
    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
        return searchResult.size

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))

        var car=searchResult.get(position)

//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)

//        Glide.with(context).load(com.mustafayusef.sharay.R.drawable.car).into(holder.view?.carImage)
        if(car.title!!.length>10)
        holder.view.card_title_search.text=car.title?.substring(0,10)+".."

       Glide.with(context).load("http://api.centralmarketiq.com/"+car.image+".png")
           .apply(RequestOptions.circleCropTransform())
           .into( holder.view.card_image_search)

    }

    fun updateList(list1: List<String>) {
    //   displayList = list1.toTypedArray()
        notifyDataSetChanged()
    }

    class CustomViewHolder(val view : View, var onNoteLisener: OnLastLisener ) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var OnNotlesener:OnLastLisener
        override fun onClick(view: View?) {
            onNoteLisener.OnLastLisener(layoutPosition)
        }

        init {
            this.OnNotlesener=onNoteLisener
            view.setOnClickListener(this)

        }


    }


    interface OnLastLisener {
        fun OnLastLisener( position: Int)
    }



}

