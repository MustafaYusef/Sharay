package com.mustafayusef.sharay.ui.sections.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.sections.Car



import kotlinx.android.synthetic.main.car_card.view.*

import kotlinx.android.synthetic.main.store_card.view.*

class storeDetailsAdapter(val context: Context, var cars:List<Car>?, val onNoteLisener:onStoreCarsClick) : RecyclerView.Adapter<storeDetailsAdapter.CustomViewHolder>(){
    //
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater = LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.car_card ,parent,false)

        return  CustomViewHolder(cardItem,mOnNotlesener)
    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
        return cars?.size!!

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))
        var carsP=cars?.get(position)


//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)

        holder.view.priceCar.text= carsP?.price.toString()+"$"
        holder.view.carMile.text=carsP?.mileage.toString()
        holder.view.modelYear.text=carsP?.year
        holder.view.carNmae.text=carsP?.title
        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP?.image+".png").into(holder.view?.carImage)

        //   Glide.with(context).load(R.drawable.car).into(holder.view?.carImage)


    }



    class CustomViewHolder(val view : View , var onNoteLisener: onStoreCarsClick ) : RecyclerView.ViewHolder(view),View.OnClickListener{
        var OnNotlesener:onStoreCarsClick
        override fun onClick(view: View?) {
            onNoteLisener.onStoreCarsClick(layoutPosition)
        }

        init {
            this.OnNotlesener=onNoteLisener
            view.setOnClickListener(this)

        }


    }


    interface onStoreCarsClick {
        fun onStoreCarsClick( position: Int)
    }

}