package com.mustafayusef.sharay.ui.main.cardetails


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.imageData
import com.mustafayusef.sharay.ui.main.bannersAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.first.view.*
import kotlinx.android.synthetic.main.full_screen.view.*
import kotlinx.android.synthetic.main.slider_layout.view.*


class sliderAdapter( val context: Context,val imges:List<imageData>,val img:String) : SliderViewAdapter<sliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
      //  viewHolder.itemView. .text = "This is slider item $position"


        if(position==0){
            Glide.with(context).load("http://api.centralmarketiq.com/"+img+".png")
                .placeholder(R.drawable.placeholder).into(  viewHolder.itemView.slid_Image)
        }else{
            var imge=imges.get(position-1).image
            Glide.with(context).load("http://api.centralmarketiq.com/"+imge+".png")
                .placeholder(R.drawable.placeholder).into(  viewHolder.itemView.slid_Image)
        }
       viewHolder.itemView.setOnClickListener {

           val dview: View = View.inflate(context,R.layout.full_screen, null)
           val builder = context?.let { AlertDialog.Builder(it,android.R.style.Theme_Black_NoTitleBar_Fullscreen).setView(dview) }
           val malert= builder?.show()

          // malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
           val adapter =sliderAdapterFull(context,imges, img)

           dview?.storeSliderFull?.sliderAdapter = adapter

           //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
//          holder.view.storeSlider?.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//          holder.view.storeSlider?.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
          // dview?.storeSliderFull?.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
           dview?.storeSliderFull?.indicatorSelectedColor = Color.WHITE
           dview?.storeSliderFull?.indicatorUnselectedColor = Color.GRAY
          // dview?.storeSliderFull?.scrollTimeInSec = 3 //set scroll delay in seconds :
           //dview?.storeSliderFull?.startAutoCycle()
       }

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return imges.size+1
    }

     class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
//        var imageViewBackground: ImageView
//        var textViewDescription: TextView

//        init {
//            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
//            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
//        }
    }
}