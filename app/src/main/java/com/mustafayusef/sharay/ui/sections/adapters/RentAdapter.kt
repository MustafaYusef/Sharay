package com.mustafayusef.sharay.ui.sections.adapters


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
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.sections.CarRent



class RentAdapter(val context:Context,val cars:List<CarRent>,val onNoteLisener:OnRentLisener ) : RecyclerView.Adapter<RentAdapter.CustomViewHolder>(){
//
  private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.car_card ,parent,false)

        return  CustomViewHolder(cardItem,mOnNotlesener)
    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
    return cars.size

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))
        var carsP=cars.get(position)


//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)

        holder.view.priceCar.text=carsP.price.toString()+"$"
        holder.view.carMile.text=carsP.mileage.toString()
        holder.view.modelYear.text=carsP.year
        holder.view.carNmae.text=carsP.title
        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png").into(holder.view?.carImage)

    }



    class CustomViewHolder(val view : View,val OnRentLisener:OnRentLisener) : RecyclerView.ViewHolder(view),View.OnClickListener{
           var OnNotlesener:OnRentLisener
        override fun onClick(view: View?) {
            OnRentLisener.OnRentLisener(layoutPosition)
        }

        init {
            this.OnNotlesener=OnRentLisener
         view.setOnClickListener(this)

        }


    }


    interface OnRentLisener {
        fun OnRentLisener( position: Int)
    }



}

