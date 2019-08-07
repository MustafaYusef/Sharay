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
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.car_details_fragment.*
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.models.banners
import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.imageData
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.databinding.CarDetailsFragmentBinding
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.main.MainCarLesener
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class carDetails : Fragment(),MainCarLesener,FavCarLesener {
    override fun onSucsess(CarResponse: CarsModel) {

    }


    private lateinit var navController: NavController
    override fun onSucsessBanners(CarResponse: banners) {

    }

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
 return inflater.inflate(R.layout.car_details_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  viewModel = ViewModelProviders.of(this).get(CarDetailsViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory= DetailsCarViewModelFactory(repostary)
        viewModel=ViewModelProviders.of(this,factory).get(CarDetailsViewModel::class.java)


        viewModel?.Auth=this
        if(arguments?.getInt("type",0)!=0){
            amount= arguments?.getInt("type")!!
        }else{
            amount=args.carId
        }


        viewModel.GetDetailsCars(amount)

        viewModel.Fav=this
        favBtnD.setOnClickListener {
            if(MainActivity.cacheObj.token!=""){
                context?.toast("click")
                favBtnD.setImageResource( R.drawable.star2)

                viewModel.AddFav(MainActivity.cacheObj.token,amount,1)
            }else{
                context?.toast("you dont have account")
            }

        }
        callNumberD.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:07712790071")
            }

                startActivity(intent)
            }
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
               // navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
               // navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
    override fun OnStart() {

    }

    override fun OnStartFav() {
       context?.toast("start Fav")
    }

    override fun onSucsessFav(CarResponse: addResFav) {
        context?.toast("Success Fav")
    }

    override fun onFailerFav(message: String) {
        context?.toast("Fail")
    }

    override fun onSucsessDetails(data: DataCarDetails) {
    //    context?.toast(data.year)

        val adapter = sliderAdapter(context!!, data.CarImages as List<imageData>)

        carImageD.setSliderAdapter(adapter)
        //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
        carImageD.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        carImageD.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        carImageD.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        carImageD.setIndicatorSelectedColor(Color.WHITE);
        carImageD.setIndicatorUnselectedColor(Color.GRAY);
        carImageD.setScrollTimeInSec(4); //set scroll delay in seconds :
        carImageD.startAutoCycle();
       ClassCar?.text= data.`class`
        airBags?.text= data.airBags
       modelCar?.text= data.brand


        //binding?.titleD=data.description
        cylinders?.text= data.cylinders.toString()
        date?.text= data.date
      //  descD?.text= data.description

        driveSystem?.text= data.driveSystem
       fuel?.text= data.fuel
        gear?.text= data.gear




       location?.text= data.location
       // carMileD.text= data.mileage.toString()
        modelCar?.text= data.brand
      name?.text= data.name
       phone?.text= data.phone
        priceCarD?.text= data.price.toString()
       roof?.text= data.roof
        seats?.text= data.seats

        status?.text= data.status
         // p.text=data.horse.toString()
        titleD?.text= data.title
      //  turbo= data.turbo.toString()
      // ty= data.type

       warid?.text= data.warid
       // wheelSize= data.wheelSize.toString()
        window?.text= data!!.window
        yearCar?.text= data.year
        location?.text=data.location
        color?.text=data.color

    }

    override fun onFailer(message: String) {
    }

}
