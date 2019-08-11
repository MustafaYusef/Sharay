package com.mustafayusef.sharay.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.main_fragment_fragment.*


import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.*
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.first.*


class Main_fragment : Fragment(),MainAdapter.OnNoteLisener,MainCarLesener {


    private lateinit var navController: NavController
    private lateinit var viewModel: MainFragmentViewModel

  var bannerResponse:List<bannersData>?=null

    companion object {
        fun newInstance() = Main_fragment()

    }


      var carId:Int = 0
      var responseCars:List<DataCars>?=null

//    internal inner class AnonymousItemClickListener : ItemClickListener {
//        fun onItemClick(view: View, position: Int) {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view= inflater.inflate(com.mustafayusef.sharay.R.layout.main_fragment_fragment, container, false)
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }







        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory=mainViewModelFactory(repostary)
        viewModel=ViewModelProviders.of(activity!!,factory).get(MainFragmentViewModel::class.java)
        viewModel.Auth =this


        viewModel.GetCars()
        //viewModel.getBanners ()
        carList?.setHasFixedSize(true)
        carList?.isDrawingCacheEnabled = true
        carList?.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        carList?.isNestedScrollingEnabled = false
        carList?.layoutManager= LinearLayoutManager(context)

        refSwap.setOnRefreshListener {

            viewModel.GetCars()
           // viewModel.getBanners()
            refSwap?.isRefreshing=false
        }
        retryBtn?.setOnClickListener {
            viewModel.GetCars()
        }
    }

    override fun onNoteClick(position: Int) {

       // var bundle = bundleOf("flage" to true)
        carId= responseCars?.get(position-1)!!.id
        var array= arrayOf(carId,1)
        val action = Main_fragmentDirections.goTocarDetails(carId)
            navController?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
                //navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
               // navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }


    override fun OnStart() {
        noNetContainer?.visibility=View.GONE
        animation_loadingMain?.visibility=View.VISIBLE
        animation_loadingMain?.playAnimation()
    }

    override fun onSucsess(CarResponse: CarsModel) {
        // viewModel.carData=CarResponse


       }

    override fun onFailer(message: String) {

        animation_loadingMain?.visibility=View.GONE
        animation_loadingMain?.pauseAnimation()
        noNetContainer?.visibility=View.VISIBLE
        carList?.visibility=View.GONE
    context?.toast(message)}
    override fun onSucsessDetails(CarResponse: DataCarDetails) {
    }
    override fun onComplete(
        carsResponse: CarsModel,
        bannerResponse: banners
    ) {
        noNetContainer?.visibility=View.GONE
        carList?.visibility=View.VISIBLE
        animation_loadingMain?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        carList?.adapter= context?.let { MainAdapter(it ,this, carsResponse.data,bannerResponse)}


        responseCars=carsResponse.data

    }
    override fun onSucsessBanners(CarResponse: banners) {
       // bannerResponse=CarResponse.data

//        animation_loadingMain?.visibility=View.GONE
//        val adapter = CarResponse?.let { bannersAdapter(context!!, it.data) }
//
//        storeSlider?.sliderAdapter = adapter
//
//        //  context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.car).into(carImageD) }
//        storeSlider?.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//        storeSlider?.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
//        storeSlider?.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
//        storeSlider?.indicatorSelectedColor = Color.WHITE
//        storeSlider?.indicatorUnselectedColor = Color.GRAY
//        storeSlider?.scrollTimeInSec = 4 //set scroll delay in seconds :
//        storeSlider?.startAutoCycle()

    }
}

