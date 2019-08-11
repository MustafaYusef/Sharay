package com.mustafayusef.sharay.ui.sections.stores

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R


import com.mustafayusef.sharay.data.models.sections.Car
import com.mustafayusef.sharay.data.models.sections.StoreDetails
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import com.mustafayusef.sharay.ui.sections.adapters.storeDetailsAdapter
import kotlinx.android.synthetic.main.details_store_fragment.*
import kotlinx.android.synthetic.main.first_store.*


class detailsStore : Fragment(),storeLesener,storeDetailsAdapter.onStoreCarsClick {



    companion object {
        fun newInstance() = detailsStore()
    }
   var phoneC=""
   var cars:List<Car>?=null
    private lateinit var viewModel: DetailsStoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_store_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val params = mFrameLayout.getLayoutParams() as CoordinatorLayout.LayoutParams
//        val behavior = params.behavior as AppBarLayout.ScrollingViewBehavior?
//        behavior?.onNestedFling(rootLayout, appbarLayout, null!!, 0f, 10000f, true)


        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }

        val repostary= SectionRepostary(api!!)
        val factory= DetailsStoreFactory(repostary)
        viewModel = ViewModelProviders.of(this,factory).get(DetailsStoreViewModel::class.java)


        viewModel?.litsener=this

        val id= arguments?.getInt("id")
        println("ffffffffffffffffffffffffffffffffffffffff id "+id)
        viewModel.storeDetails(id!!)



    }
    override fun onFailer(message: String) {
        context?.toast(message)
    }

    override fun onSeccess(response: StoreDetails) {
        println(response)

        response.let {it1->


//            context?.let {
//                Glide.with(it).load("http://api.centralmarketiq.com/"+response?.data.image+".png")
//                    .into(imageStotre) }
//            storeName.text=response.data.name
//            phoneC= response.data.phone
//            locDStore.text=response.data.location

            cars=response?.data?.Cars
            carStoreList?.layoutManager= LinearLayoutManager(context)
            carStoreList?.adapter= context?.let { storeDetailsAdapter(it , it1.data,this)}
        }


    }

    override fun OnStart() {
        context?.toast("start")

    }
    override fun onStoreCarsClick(position: Int) {
        val carId= cars?.get(position-1)!!.id
        val action = detailsStoreDirections .detailsStoreToCarDetails(carId)
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
}
