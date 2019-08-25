package com.mustafayusef.sharay.ui.sections.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.sections.Data


import kotlinx.android.synthetic.main.car_card.view.*
import kotlinx.android.synthetic.main.desc_parts.view.*
import kotlinx.android.synthetic.main.first_store.*
import kotlinx.android.synthetic.main.first_store.view.*
import kotlinx.android.synthetic.main.first_store.view.imageStotre
import kotlinx.android.synthetic.main.first_store.view.locDStore
import kotlinx.android.synthetic.main.first_store.view.storeName

class storeDetailsAdapter(val context: Context, var cars: Data, val onNoteLisener:onStoreCarsClick) : RecyclerView.Adapter<storeDetailsAdapter.CustomViewHolder>(){
    //
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)
        if(viewType==1){
            val layoutInflater =LayoutInflater.from(parent.context)
            val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.car_card ,parent,false)

            return  CustomViewHolder(cardItem,mOnNotlesener)
        }else{
            val layoutInflater = LayoutInflater.from(parent.context)
            val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.first_store ,parent,false)

            return  CustomViewHolder(cardItem,mOnNotlesener)
        }

    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
        return cars?.Cars?.size!!+1

    }
    override fun getItemViewType(position: Int): Int {
        if(position==0){
            return 0
        }
        else{
            return 1
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))
        if (position==0){
            context?.let {
                Glide.with(it).load("http://api.centralmarketiq.com/"+cars.image+".png")
                    .into(holder.view.imageStotre) }
            holder.view.storeName.text=cars.name

            holder.view. locDStore.text=cars.location
            holder.view.callStore.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${cars.phone}")
                }

                context. startActivity(intent)
            }

        }else{
            var carsP=cars?.Cars?.get(position-1)


//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)

            if(carsP?.price==0){
                holder.view.priceCar.text=context.resources.getString(R.string.callUs)
            }else{
                holder.view.priceCar.text=carsP?.price.toString()
            }
            holder.view.carMile.text=carsP?.mileage.toString()+" mi"
            holder.view.modelYear.text=carsP?.year
            holder.view.carNmae.text=carsP?.title
            Glide.with(context).load("http://api.centralmarketiq.com/"+carsP?.image+".png")
                .placeholder(R.drawable.placeholder).into(holder.view?.carImage)


            holder.view.callNumber.setOnClickListener {
                val dview: View = View.inflate(context, R.layout.desc_parts, null)
                val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
                val malert= builder?.show()
                dview.title.visibility=View.GONE
                malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dview.descPart?.text=context?.getResources().getString(R.string.doYouCall)
                dview.conform?.visibility=View.VISIBLE
                dview.conform?.setOnClickListener {
                    malert?.dismiss()
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${carsP!!.phone}")
                    }

                    context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
                }
            }
        }

        //   Glide.with(context).load(R.drawable.car).into(holder.view?.carImage)


    }



    class CustomViewHolder(val view : View , var onNoteLisener: onStoreCarsClick ) : RecyclerView.ViewHolder(view),View.OnClickListener{
        var OnNotlesener:onStoreCarsClick
        override fun onClick(view: View?) {
            if(layoutPosition !=0)
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