package com.mustafayusef.sharay.ui.profile.myAds






import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R

import com.mustafayusef.sharay.data.models.userModels.UserCars
import com.mustafayusef.sharay.ui.profile.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.car_card_favorite.view.*
import kotlinx.android.synthetic.main.desc_parts.view.*
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.auth.signup.Login


class myCarsAdapter(
    val context: Context,
    var onNoteLisener: OnNoteLisener,
    val cars: List<UserCars>,
    val viewModel: ProfileFragmentViewModel
) : RecyclerView.Adapter<myCarsAdapter.CustomViewHolder>(){
//

  private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(com.mustafayusef.sharay.R.layout.car_card_favorite ,parent,false)

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
           if(carsP.active){
               holder.view. state.text=context?.getResources().getString(R.string.Approved)
               holder.view. con.setBackgroundResource(R.drawable.revers)
           }else{
               holder.view. state.text=context?.getResources().getString(R.string.Pending)
               holder.view. con.setBackgroundResource(R.drawable.reverse2)
           }

        if(carsP.price==0){
            holder.view.priceCar.text=context.resources.getString(R.string.callUs)
        }else{
            holder.view.priceCar.text=carsP.price.toString()
        }

        holder.view.carMile.text=carsP.mileage.toString()+" mi"
        holder.view.modelYear.text=carsP.year
        holder.view.carNmae.text=carsP.title
        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png")
            .placeholder(R.drawable.placeholder).into(holder.view?.carImage)
        holder.view. deleteBtn.setOnClickListener {
            val dview: View = View.inflate(context,R.layout.desc_parts, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            dview.title.visibility=View.GONE
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview.descPart?.text=context?.getResources().getString(R.string.doYou)
            dview.conform?.visibility=View.VISIBLE
            dview.conform?.setOnClickListener {
                viewModel.DeletMyCar(MainActivity.cacheObj  .token,carsP.id)
                malert?.dismiss()
                context?.toast(context?.getResources().getString(R.string.deletSucc))

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
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

