package com.mustafayusef.sharay.ui.sections.motors

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.sections.MotorImage
import com.mustafayusef.sharay.data.models.sections.motorDataDetails
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.main.cardetails.sliderAdapter
import com.mustafayusef.sharay.ui.sections.carrent.CarRentFactory
import com.mustafayusef.sharay.ui.sections.carrent.CareRentViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.car_details_fragment.*
import kotlinx.android.synthetic.main.motor_details_fragment.*

class motorDetails : Fragment(),motorDLesener {


    companion object {
        fun newInstance() = motorDetails()
    }

    private lateinit var viewModel: MotorDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.motor_details_fragment, container, false)
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= SectionRepostary(api!!)
        val factory= motorDetailsFactory(repostary)
        viewModel = ViewModelProviders.of(this,factory).get(MotorDetailsViewModel::class.java)
        viewModel.lesener  =this
        val id= arguments?.getInt("id")
       // println("ffffffffffffffffffffffffffffffffffffffff id "+id)
        viewModel.GetDetailsMotor(id!!)

    }
    override fun OnStart() {
   context?.toast("start motor")
    }

    override fun onSucsess(CarResponse: motorDataDetails) {
        val adapter = sliderAdapterMotor(context!!, CarResponse.MotorImages as List<MotorImage>)

        MotorImageD.setSliderAdapter(adapter)
        //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
        MotorImageD.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        MotorImageD.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        MotorImageD.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        MotorImageD.setIndicatorSelectedColor(Color.WHITE);
        MotorImageD.setIndicatorUnselectedColor(Color.GRAY);
        MotorImageD.setScrollTimeInSec(4); //set scroll delay in seconds :
        MotorImageD.startAutoCycle();

        motorNameD.text=CarResponse.name
        modelMotorD.text=CarResponse.type
        yearMotorD.text=CarResponse.year
        statusMotorD.text=CarResponse.status
        colorMotorD.text=CarResponse.color
        mileMotorD.text=CarResponse.miles
        priceMotorD.text=CarResponse.price.toString()
        desMotorD.text=CarResponse.description
        nameMotorD.text=CarResponse.sell_name
        statesMD.text=CarResponse.state
        phoneDmOTOR.text=CarResponse.phone




    }

    override fun onFailer(message: String) {
        context?.toast(message)
    }
}
