package com.mustafayusef.sharay.ui.main.cardetails


import android.content.Context
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafayusef.sharay.R
import kotlinx.android.synthetic.main.slider_layout.view.*


class sliderAdapter( val context: Context) : SliderViewAdapter<sliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
      //  viewHolder.itemView. .text = "This is slider item $position"

        when (position) {
            0 -> Glide.with(viewHolder.itemView)
                .load(com.mustafayusef.sharay.R.drawable.car)
                .into(viewHolder.itemView.slid_Image)
            1 -> Glide.with(viewHolder.itemView)
                .load(com.mustafayusef.sharay.R.drawable.car)
                .into(viewHolder.itemView.slid_Image)

            2 -> Glide.with(viewHolder.itemView)
                .load(com.mustafayusef.sharay.R.drawable.car)
                .into(viewHolder.itemView.slid_Image)
            else -> Glide.with(viewHolder.itemView)
                .load(com.mustafayusef.sharay.R.drawable.car)
                .into(viewHolder.itemView.slid_Image)
        }

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return 4
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