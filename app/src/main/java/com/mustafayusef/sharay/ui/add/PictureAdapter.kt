package com.mustafayusef.sharay.ui.add




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
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View.OnClickListener
import kotlinx.android.synthetic.main.added_image_card.view.*



class PictureAdapter(val context:Context,var onNoteLisener: OnNoteLisener,var ListUri:Array<Uri>) : RecyclerView.Adapter<PictureAdapter.CustomViewHolder>(){
    //
    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.added_image_card ,parent,false)

        return  CustomViewHolder(cardItem,mOnNotlesener)
    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
        return ListUri.count()

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))



//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)
             val bitmap= MediaStore.Images.Media.getBitmap(context.contentResolver,ListUri.get(position))

         val bitmapDrawable= BitmapDrawable(bitmap)
         if(position==0){
             holder.view?.mainImage.visibility=View.VISIBLE
         }

        Glide.with(context).load(bitmapDrawable).into(holder.view?.carImage1)

    }



    class CustomViewHolder(val view : View, var onNoteLisener: OnNoteLisener ) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var OnNotlesener:OnNoteLisener
        override fun onClick(view: View?) {
            onNoteLisener.onNoteClick(layoutPosition )
        }

        init {
            this.OnNotlesener=onNoteLisener
            view.deleteImage.setOnClickListener(this)

        }


    }


    interface OnNoteLisener {
        fun onNoteClick( position: Int)
    }



}

