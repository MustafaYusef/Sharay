package com.mustafayusef.sharay.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.main_fragment_fragment.*


import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity


class Main_fragment : Fragment(),MainAdapter.OnNoteLisener,MainCarLesener {
    private lateinit var viewModel: MainFragmentViewModel



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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory=mainViewModelFactory(repostary)
        viewModel=ViewModelProviders.of(activity!!,factory).get(MainFragmentViewModel::class.java)
        viewModel?.Auth=this

        viewModel.GetCars()

        carList?.setHasFixedSize(true)
        carList?.isDrawingCacheEnabled = true
        carList?.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        carList?.isNestedScrollingEnabled = false
        carList?.layoutManager= LinearLayoutManager(context)




    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onNoteClick(position: Int) {

        carId= responseCars?.get(position)!!.id
        val action = Main_fragmentDirections.goTocarDetails(carId)
        view?.findNavController()?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE
            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }


    override fun OnStart() {

        animation_loadingMain.visibility=View.VISIBLE
        animation_loadingMain.playAnimation()
    }

    override fun onSucsess(CarResponse: CarsModel) {
        // viewModel.carData=CarResponse
        animation_loadingMain.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        carList?.adapter= context?.let { MainAdapter(it ,this, CarResponse.data)}
        responseCars=CarResponse.data}

    override fun onFailer(message: String) {

        animation_loadingMain.visibility=View.GONE
        animation_loadingMain.pauseAnimation()
    context?.toast(message)}
    override fun onSucsessDetails(CarResponse: DataCarDetails) {
    }
}

