package com.mustafayusef.sharay.ui.sections.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.mustafayusef.sharay.data.models.sections.StoresData

import kotlinx.android.synthetic.main.store_card.view.*

class storesAdapter(val context: Context, val cars:List<StoresData>,val onNoteLisener:onStoreClick) : RecyclerView.Adapter<storesAdapter.CustomViewHolder>(){
    //
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater = LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.store_card ,parent,false)

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
        holder.view.storeTitle .text=carsP.name .toString()+"$"

        holder.view.phoneStore .text=carsP.phone .toString()+"$"
        holder.view.locStore .text=carsP.location .toString()


        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png")
            .into(holder.view?.storeImage)

    }



    class CustomViewHolder(val view : View , var onNoteLisener: onStoreClick ) : RecyclerView.ViewHolder(view),View.OnClickListener{
        var OnNotlesener:onStoreClick
        override fun onClick(view: View?) {
            onNoteLisener.onStoreClick(layoutPosition)
        }

        init {
            this.OnNotlesener=onNoteLisener
            view.setOnClickListener(this)

        }


    }


    interface onStoreClick {
        fun onStoreClick( position: Int)
    }

}