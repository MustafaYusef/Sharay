package com.mustafayusef.sharay.ui.main.cardetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.car_details_fragment.*
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.databinding.CarDetailsFragmentBinding
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.main.MainCarLesener
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.car_card.*
import kotlinx.android.synthetic.main.car_card_favorite.*


class carDetails : Fragment(),MainCarLesener {
    var amount =0
    var binding: CarDetailsFragmentBinding?=null
    val args: carDetailsArgs by navArgs()
    companion object {
        fun newInstance() = carDetails()
    }

    private lateinit var viewModel: CarDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory= DetailsCarViewModelFactory(repostary)
        binding= DataBindingUtil.inflate(inflater, R.layout.car_details_fragment,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(CarDetailsViewModel::class.java)
        binding?.viewmodel=viewModel

        viewModel?.Auth=this
        binding?.executePendingBindings()



        return binding?.root



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  viewModel = ViewModelProviders.of(this).get(CarDetailsViewModel::class.java)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amount=args.carId
        viewModel.GetDetailsCars(amount)


        val adapter = sliderAdapter(context!!)

        carImageD.setSliderAdapter(adapter)
        //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
        carImageD.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        carImageD.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        carImageD.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        carImageD.setIndicatorSelectedColor(Color.WHITE);
        carImageD.setIndicatorUnselectedColor(Color.GRAY);
        carImageD.setScrollTimeInSec(4); //set scroll delay in seconds :
        carImageD.startAutoCycle();
        callNumberD.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:07712790071")
            }

                startActivity(intent)
            }




    }
    override fun OnStart() {

    }

    override fun onSucsess(CarResponse: CarsModel) {
    }

    override fun onSucsessDetails(data: DataCarDetails) {
    //    context?.toast(data.year)

       ClassCar.text= data.`class`
        airBags.text= data.airBags
       modelCar.text= data.brand
        if(data.camera){
            cameraD.text="yes"
        }else{
            cameraD.text="No"
        }

        //binding?.titleD=data.description
        cylinders.text= data.cylinders.toString()
        date.text= data.date
        descD.text= data.description
        doors.text= data.doors
        driveSystem.text= data.driveSystem
       fuel.text= data.fuel
        gear.text= data.gear
       horse.text= data.horse.toString()
        if(data.isUsed){
          status.text="Used"
        }else{
            status.text="New"
        }

       lamps.text= data.lamps
       location.text= data.location
       // carMileD.text= data.mileage.toString()
        modelCar.text= data.model
      name.text= data.name
       phone.text= data.phone
        priceCarD.text= data.price.toString()
       roof.text= data.roof
        seats.text= data.seats

       sensors.text= data.sensors
        status.text= data.status
         // p.text=data.horse.toString()
        titleD.text= data.title
      //  turbo= data.turbo.toString()
      // ty= data.type

       warid.text= data.warid
       // wheelSize= data.wheelSize.toString()
        window.text= data!!.window
        yearCar.text= data.year
        location.text=data.location
        color.text=data.color

    }

    override fun onFailer(message: String) {
    }
}
