package com.mustafayusef.sharay.ui.sections.adapters


import kotlinx.android.synthetic.main.car_card.view.*



import android.content.Context


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.graphics.Color
import com.mustafayusef.sharay.data.models.DataCars
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.first.view.*


class CarSaleAdapter(
    val context: Context,
    var onNoteLisener: OnNoteLisener,
    val cars: List<DataCars>
) : RecyclerView.Adapter<CarSaleAdapter.CustomViewHolder>(){
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

          holder.view.priceCar.text=carsP.price.toString()+"$"
          holder.view.carMile.text=carsP.mileage.toString()
          holder.view.modelYear.text=carsP.year
          holder.view.carNmae.text=carsP.title
          Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png").into(holder.view?.carImage)



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

