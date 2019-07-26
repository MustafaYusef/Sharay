package com.mustafayusef.sharay.ui.sections.carRent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mustafayusef.holidaymaster.networks.networkIntercepter

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.main.MainFragmentViewModel
import com.mustafayusef.sharay.ui.main.mainViewModelFactory

class CareRent : Fragment(),rentLesener {
    val args: CareRentArgs by navArgs()
    var type:String?=null
    companion object {
        fun newInstance() = CareRent()
    }

    private lateinit var viewModel: CareRentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.public_fragment, container, false)
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
        val factory= mainViewModelFactory(repostary)
        viewModel = ViewModelProviders.of(activity!!,factory).get(CareRentViewModel::class.java)


        viewModel?.lesener=this
        type=args.Section
        viewModel.GetRent()
        // TODO: Use the ViewModel
    }
    override fun OnStart() {
    }

    override fun onSucsess(CarResponse: CarsModel) {
    }



    override fun onFailer(message: String) {
    }
}
