package com.mustafayusef.sharay.ui.profile.myFavarote


import kotlinx.android.synthetic.main.car_card.view.*



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
import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import kotlinx.android.synthetic.main.car_card.view.carNmae
import kotlinx.android.synthetic.main.car_card.view.modelYear
import kotlinx.android.synthetic.main.car_card.view.priceCar
import kotlinx.android.synthetic.main.desc_parts.view.*


class myFavAdapter(val context:Context, var onNoteLisener: OnNoteLisener, val cars: List<favoriteModel>) : RecyclerView.Adapter<myFavAdapter.CustomViewHolder>(){
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
        carsP.let {
            if(carsP.Car!!.price==0){
                holder.view.priceCar.text=context.resources.getString(R.string.callUs)
            }else{
                holder.view.priceCar.text=carsP.Car!!.price.toString()
            }
            holder.view.carMile.text = carsP.Car?.mileage.toString()+" mi"
            holder.view.modelYear.text = carsP.Car?.year
            holder.view.carNmae.text = carsP.Car?.name
            Glide.with(context).load("http://api.centralmarketiq.com/" + carsP.Car?.image + ".png")
                .placeholder(R.drawable.placeholder)
                .into(holder.view?.carImage)

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
                        data = Uri.parse("tel:${carsP.Car!!.phone}")
                    }

                    context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
                }
            }
        }

    }



    class CustomViewHolder(val view : View, var onNoteLisener: OnNoteLisener ) : RecyclerView.ViewHolder(view), View.OnClickListener{
           var OnNotlesener:OnNoteLisener
        override fun onClick(view: View?) {
           onNoteLisener.onNoteClick(layoutPosition)
        }

        init {
            this.OnNotlesener=onNoteLisener
         view.setOnClickListener(this)

        }


    }


    interface OnNoteLisener {
        fun onNoteClick( position: Int)
    }



}

