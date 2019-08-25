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
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.sections.CarRent
import kotlinx.android.synthetic.main.desc_parts.view.*


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

        if(carsP.price==0){
            holder.view.priceCar.text=context.resources.getString(com.mustafayusef.sharay.R.string.callUs)
        }else{
            holder.view.priceCar.text=carsP.price.toString()
        }
        holder.view.carMile.text=carsP.mileage.toString()+" mi"
        holder.view.modelYear.text=carsP.year
        holder.view.carNmae.text=carsP.title
        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png")
            .placeholder(com.mustafayusef.sharay.R.drawable.placeholder).into(holder.view?.carImage)


        holder.view.callNumber.setOnClickListener {
            val dview: View = View.inflate(context, com.mustafayusef.sharay.R.layout.desc_parts, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            dview.title.visibility=View.GONE
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview.descPart?.text=context?.getResources().getString(com.mustafayusef.sharay.R.string.doYouCall)
            dview.conform?.visibility=View.VISIBLE
            dview.conform?.setOnClickListener {
                malert?.dismiss()
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${carsP.phone}")
                }

                context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
            }
        }
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

