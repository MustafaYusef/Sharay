package com.mustafayusef.sharay.ui.main


import android.content.Context
import android.content.Intent
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mustafayusef.sharay.R

import com.mustafayusef.sharay.data.models.bannersData
import com.mustafayusef.sharay.ui.main.carStores.storeCar
import kotlinx.android.synthetic.main.car_card.view.*
import kotlinx.android.synthetic.main.slider_layout.view.*


class bannersAdapter(val context: Context,val images:List<bannersData>)
    : SliderViewAdapter<bannersAdapter.SliderAdapterVH>() {
//    private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        if (position==0){
            Glide.with(context).load(R.drawable.first_banner).placeholder(R.drawable.placeholder).into( viewHolder.itemView.slid_Image)

        }else{
            var im=images.get(position-1)
            Glide.with(context).load("http://api.centralmarketiq.com/"+im.image+".png")
                .placeholder(R.drawable.placeholder)
                .into( viewHolder.itemView.slid_Image)
            viewHolder.itemView.setOnClickListener {
                var bundle = bundleOf("id" to im.storeId)
                viewHolder.itemView.findNavController()?.navigate(R.id.detailsStore,bundle)
            }

//            viewHolder.itemView.setOnClickListener {
//
//                    val intent = Intent(context, storeCar::class.java)
//                       intent.putExtra("StoreId",im.storeId)
//                    context.startActivity(intent)
//
//
//
//
//            }
//            viewHolder.itemView.findNavController()?.navigate(R.id.action_main_fragment_to_detailsStore, bundle)
        }



    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return images.count()+1
    }

     class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
//         var OnNotlesener: MainAdapter.OnNoteLisener
//         override fun onClick(view: View?) {
//
//                 onNoteLisener.onNoteClick(Pos )
//         }
//
//         init {
//             this.OnNotlesener=onNoteLisener
//             itemView.setOnClickListener(this)
//
//         }
    }

}