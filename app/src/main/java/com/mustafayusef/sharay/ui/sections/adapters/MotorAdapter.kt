package com.mustafayusef.sharay.ui.sections.adapters


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
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.sections.motorData
import kotlinx.android.synthetic.main.desc_parts.view.*


class MotorAdapter(
    val context: Context,
    var onNoteLisener: OnMotorLisener,
    val cars: List<motorData>
) : RecyclerView.Adapter<MotorAdapter.CustomViewHolder>(){
//
  private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

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

          var carsP=cars.get(position)


//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)
        holder.view.con.visibility=View.INVISIBLE
          holder.view.priceCar.text=carsP.price.toString()
          holder.view.carMile.text=carsP.miles .toString()
          holder.view.modelYear.text=carsP.year
          holder.view.carNmae.text=carsP.title
          Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png").into(holder.view?.carImage)


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
                    data = Uri.parse("tel:${carsP.phone}")
                }

                context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
            }
        }
    }



    class CustomViewHolder(val view: View, var OnMotorLisener:OnMotorLisener) : RecyclerView.ViewHolder(view), View.OnClickListener{
           var OnNotlesener:OnMotorLisener
        override fun onClick(view: View?) {

            OnMotorLisener.OnMotorLisener(layoutPosition)
        }

        init {
            this.OnNotlesener=OnMotorLisener
         view.setOnClickListener(this)

        }


    }


    interface OnMotorLisener {
        fun OnMotorLisener( position: Int)
    }



}

