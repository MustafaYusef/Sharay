package com.mustafayusef.sharay.ui.sections.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mustafayusef.sharay.data.models.sections.partsData

import kotlinx.android.synthetic.main.part_card.view.*


class partsAdapter(val context: Context, val cars:List<partsData>, val onNoteLisener: OnPartLisener ) : RecyclerView.Adapter<partsAdapter.CustomViewHolder>(){
    //
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater = LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.part_card ,parent,false)

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

        holder.view.titlePart .text=carsP.name .toString()
        holder.view.phonePart .text=carsP.phone .toString()
        holder.view.locPart .text=carsP.location .toString()
        holder.view.detailsPart .text=carsP.detailes .toString()
        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png").into(holder.view?.partImage)

    }



    class CustomViewHolder(val view : View, var onNoteLisener: OnPartLisener) : RecyclerView.ViewHolder(view),View.OnClickListener{
        var OnNotlesener:OnPartLisener
        override fun onClick(view: View?) {
            onNoteLisener.OnPartLisener(layoutPosition)
        }

        init {
            this.OnNotlesener=onNoteLisener
            view.setOnClickListener(this)

        }


    }


    interface OnPartLisener {
        fun OnPartLisener( position: Int)
    }


}