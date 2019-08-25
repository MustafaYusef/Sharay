package com.mustafayusef.sharay.ui.main


import kotlinx.android.synthetic.main.car_card.view.*



import android.content.Context
import android.content.Intent


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.banners
import com.mustafayusef.sharay.ui.MainActivity
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.car_card.view.carImage
import kotlinx.android.synthetic.main.car_card.view.carMile
import kotlinx.android.synthetic.main.car_card.view.carNmae
import kotlinx.android.synthetic.main.car_card.view.modelYear
import kotlinx.android.synthetic.main.car_card.view.priceCar
import kotlinx.android.synthetic.main.car_card_favorite.view.*
import kotlinx.android.synthetic.main.desc_parts.view.*
import kotlinx.android.synthetic.main.first.*
import kotlinx.android.synthetic.main.first.view.*
import kotlinx.android.synthetic.main.first.view.storeSlider
import kotlinx.android.synthetic.main.main_fragment_fragment.*


class MainAdapter(
    val context: Context,
    var onNoteLisener: OnNoteLisener,
    val cars: List<DataCars>,val banners:banners
) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>(){
//
  private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)
         if(viewType==1){
             val layoutInflater =LayoutInflater.from(parent.context)
             val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.car_card ,parent,false)

             return  CustomViewHolder(cardItem,mOnNotlesener)
         }
        else{
            val layoutInflater =LayoutInflater.from(parent.context)
             val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.first,parent,false)

             return  CustomViewHolder(cardItem,mOnNotlesener)
         }

    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
    return cars.size+1

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
      if(position==0){
         // animation_loadingMain?.visibility=View.GONE
          val adapter = banners?.let { bannersAdapter(context!!, it.data) }

          holder.view.storeSlider?.sliderAdapter = adapter

          //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
//          holder.view.storeSlider?.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//          holder.view.storeSlider?.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
         holder.view. storeSlider?.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
          holder.view.storeSlider?.indicatorSelectedColor = Color.WHITE
          holder.view. storeSlider?.indicatorUnselectedColor = Color.GRAY
          holder.view. storeSlider?.scrollTimeInSec = 3 //set scroll delay in seconds :
          holder.view. storeSlider?.startAutoCycle()
      }
        else{
          var carsP=cars.get(position-1)


//        Glide.with(context).load(holidays?.logoCover).apply(RequestOptions.centerCropTransform().circleCrop()).into(holder.view.LogoAir)
         if(carsP.price==0){
             holder.view.priceCar.text=context.resources.getString(R.string.callUs)
         }else{
             holder.view.priceCar.text=carsP.price.toString()
         }

          holder.view.carMile.text=carsP.mileage.toString()+" mi"
          holder.view.modelYear.text=carsP.year
          holder.view.carNmae.text=carsP.title
          Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png")
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
                      data = Uri.parse("tel:${carsP.phone}")
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
            if (layoutPosition!=0)
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

