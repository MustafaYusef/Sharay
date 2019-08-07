package com.mustafayusef.sharay.ui.sections.motors


import android.content.Context
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.imageData
import com.mustafayusef.sharay.data.models.sections.MotorImage
import kotlinx.android.synthetic.main.slider_layout.view.*


class sliderAdapterMotor(val context: Context, val imges:List<MotorImage>) : SliderViewAdapter<sliderAdapterMotor.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
      //  viewHolder.itemView. .text = "This is slider item $position"

        var imge=imges.get(position).image
        Glide.with(context).load("http://api.centralmarketiq.com/"+imge+".png").into(  viewHolder.itemView.slid_Image)


    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return imges.size
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