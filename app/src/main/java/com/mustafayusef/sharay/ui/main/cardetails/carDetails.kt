package com.mustafayusef.sharay.ui.main.cardetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AlertDialog
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
import com.mustafayusef.sharay.data.models.userModels.Favorite

import com.mustafayusef.sharay.data.networks.delete
import com.mustafayusef.sharay.data.networks.myApi

import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary

import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.auth.signup.Login
import com.mustafayusef.sharay.ui.main.MainCarLesener
import com.mustafayusef.sharay.ui.profile.Profile_fragment
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.info.view.*


class carDetails : Fragment(),MainCarLesener,FavCarLesener {
    override fun onComplete(
        carsResponse: CarsModel,
        bannerResponse: banners
    ) {
    }


    override fun onSucsess(CarResponse: CarsModel) {

    }


    private lateinit var navController: NavController
    override fun onSucsessBanners(CarResponse: banners) {

    }
    var isAdd=false
    var isClick=true
    var flage:Boolean=false
   var phoneNum=""
    var amount =0
    var favId =0
  //  var binding: CarDetailsFragmentBinding?=null
    val args: carDetailsArgs by navArgs()
    companion object {
        fun newInstance() = carDetails()
    }

    private lateinit var viewModel: CarDetailsViewModel
    var imagesd:MutableList<String> = arrayListOf()
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


//        val networkIntercepter2= context?.let { networkIntercepter(it) }
//        val apiFav= networkIntercepter2?.let { MyFav(it) }
//        val Favrepostary= MyFavRepostary(apiFav!!)

        val factory= DetailsCarViewModelFactory(repostary)
        viewModel=ViewModelProviders.of(this,factory).get(CarDetailsViewModel::class.java)


        viewModel?.Auth=this

        if(arguments?.getInt("type",-1)!=-1){
            amount= arguments?.getInt("type")!!
        }else{
            amount=args.carId
        }


        viewModel.GetDetailsCars(amount)
//        if(MainActivity.cacheObj.token!=""){
//            favContainer?.visibility=View.VISIBLE
//            viewModel.profile(MainActivity.cacheObj.token)
//        }

        viewModel.Fav=this
        favBtnD?.setOnClickListener {
            if(MainActivity.cacheObj  .token!=""){

                    if(flage){

                            //context?.toast("delete")
                            viewModel.DeleteFavorite(MainActivity.cacheObj.token, MainActivity.cacheObj.id, favId)
                            favBtnD?.setImageResource(R.drawable.star)


                        } else {
                        // context?.toast("add")
                        viewModel.AddFav (MainActivity.cacheObj .token,amount,1)
                        favBtnD?.setImageResource( R.drawable.star2)

                    }



            }else{
                context?.toast(resources.getString(R.string.haveAcount))
            }

        }
        callNumberD.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNum")
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
        detailsContainer?.visibility=View.INVISIBLE
        animation_loadingCarDetails?.playAnimation()
        animation_loadingCarDetails?.visibility=View.VISIBLE
    }

    override fun OnStartFav() {
        isClick=false
        favBtnD?.visibility=View.GONE
     //   context?.toast("start Fav")
//        favBtnD?.isClickable=false
//        favBtnD?.isEnabled=false
    }
    override fun onSucsessDel(CarResponse: delete) {
        favBtnD?.setImageResource( R.drawable.star)
        flage=false
        isClick=true
       // context?.toast(CarResponse.data)
        favBtnD?.visibility=View.VISIBLE


    }
    override fun onSucsessFav(CarResponse: addResFav) {
        favBtnD?.setImageResource( R.drawable.star2)
        flage=true
        isClick=true
        isAdd=false
       // context?.toast("Success add")
        favBtnD?.visibility=View.VISIBLE
        favBtnD?.isClickable=true
        favBtnD?.isEnabled=true
        isAdd=false
    }

    override fun onFailerFav(message: String) {
        favBtnD?.isClickable=true
        favBtnD?.isEnabled=true
        favBtnD?.visibility=View.VISIBLE
        context?.toast(message)

    }

    override fun onSucsessDetails(data: DataCarDetails) {
        detailsContainer?.visibility=View.VISIBLE
        animation_loadingCarDetails?.pauseAnimation()
        animation_loadingCarDetails?.visibility=View.GONE
        if(data.isImported){
            favContainer?.visibility=View.INVISIBLE

        }else if(data.isRent){
            favContainer?.visibility=View.INVISIBLE
        }else{
            favContainer?.visibility=View.VISIBLE
        }
       // data.i
    //    context?.toast(data.year)
        phoneNum=data.phone
        val adapter = context?.let { sliderAdapter(it,data.CarImages as List<imageData>,data.image)}

        carImageD?.setSliderAdapter(adapter)
        //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
       // carImageD?.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        carImageD?.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        carImageD?.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        carImageD?.setIndicatorSelectedColor(Color.WHITE);
        carImageD?.setIndicatorUnselectedColor(Color.GRAY);
        carImageD?.setScrollTimeInSec(4); //set scroll delay in seconds :
        carImageD?.startAutoCycle();
       ClassCar?.text= data.`class`

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
        if(data.price==0){
            priceCarD?.text=resources.getString(R.string.callUs)
        }else{
            priceCarD?.text= data.price.toString()
        }

       roof?.text= data.roof

        horse?.text= data.cylinders.toString()
        status?.text= data.status
         // p.text=data.horse.toString()
        titleD?.text= data.title
      //  turbo= data.turbo.toString()
      // ty= data.type
        desD?.text=data.description
       warid?.text= data.warid
       // wheelSize= data.wheelSize.toString()

        douchmD?.text=data.type
        yearCar?.text= data.year
        location?.text=data.location
        color?.text=data.color
    viewModel.profile(MainActivity.cacheObj  .token)
    }

    override fun onFailer(message: String) {
        detailsContainer?.visibility=View.VISIBLE
        animation_loadingCarDetails?.pauseAnimation()
        animation_loadingCarDetails?.visibility=View.GONE

    }

    override fun OnStartProfile() {

    }

    override fun onSucsessProfile(CarResponse: List<Favorite>) {
       // favContainer.visibility=View.VISIBLE

        for (i in CarResponse){
            if(i.carId==amount){
               favId= i.id
                flage=true
                isAdd=false
                break
            }
        }
      //  context?.toast("starat check")
        if(flage){
            favBtnD?.setImageResource( R.drawable.star2)
        }else{
            favBtnD?.setImageResource( R.drawable.star)
        }
    }

    override fun onFailerProfile(message: String) {
        context?.toast(message)
    }

}
