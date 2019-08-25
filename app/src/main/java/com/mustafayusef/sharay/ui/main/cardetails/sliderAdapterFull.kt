package com.mustafayusef.sharay.ui.main.cardetails


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.imageData
import com.mustafayusef.sharay.ui.main.bannersAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.first.view.*
import kotlinx.android.synthetic.main.full_screen.view.*
import kotlinx.android.synthetic.main.slider_layout.view.*
import kotlinx.android.synthetic.main.slider_layout_full.view.*


class sliderAdapterFull(val context: Context, val imges:List<imageData>, val img:String) : SliderViewAdapter<sliderAdapterFull.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout_full, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
      //  viewHolder.itemView. .text = "This is slider item $position"


        if(position==0){
            Glide.with(context).load("http://api.centralmarketiq.com/"+img+".png")
                .placeholder(R.drawable.placeholder).into(  viewHolder.itemView.slid_ImageFull)
        }else{
            var imge=imges.get(position-1).image
            Glide.with(context).load("http://api.centralmarketiq.com/"+imge+".png")
                .placeholder(R.drawable.placeholder).into(  viewHolder.itemView.slid_ImageFull)
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