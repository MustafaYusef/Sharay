package com.mustafayusef.sharay.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding2.widget.textChanges
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R


import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsSearchRepostary
import com.mustafayusef.sharay.data.utils.PostsDiffUtilCallback
import com.mustafayusef.sharay.database.databaseApp
import com.mustafayusef.sharay.database.entitis.latestCar

import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search_fragment.*



class searchFragment : Fragment(),lastSearchAdapter.OnLastLisener,searchLesener,searchResultAdapter.OnNoteLisener {



    var carId:Int = 0
    var responseCars:List<latestCar>?=null
    var listCars:List<DataCars>?=null
 var temp:MutableList<DataCars> = arrayListOf()
    companion object {
        fun newInstance() = searchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(com.mustafayusef.sharay.R.layout.search_fragment, container, false)
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeSearch.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }
    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val db= context?.let { databaseApp(it) }
        val repostary= CarsSearchRepostary(api!!,db!!)
        val factory= CarSearchFactory(repostary)
        viewModel = ViewModelProviders.of(this,factory).get(SearchViewModel::class.java)
        viewModel?.litsener=this

        list_of_search_cars?.setHasFixedSize(true)
        list_of_search_cars?.isDrawingCacheEnabled = true
        list_of_search_cars?.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        list_of_search_cars?.isNestedScrollingEnabled = false
        viewModel.doSearch()
        viewModel.getData()



        searchCars.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // do something on text submit
                filter(query.toString())
                up()
                lastContainer?.visibility=View.GONE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                filter(newText.toString())
//                up()
                return false
            }
        })


    }
    fun up(){
      //  list_of_search_cars?.layoutManager= LinearLayoutManager(context)
        list_of_search_cars.adapter=context?.let { searchResultAdapter(it ,this, temp!!) }

    }
    fun filter(text:String){
        context?.toast("start filter")
          temp.clear()
        for(te in this!!.listCars!!){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(te.title.contains(text.toLowerCase())){
                temp.add(te)
            }
        }
   }
    override fun onFailerSerach(message: String) {
        context?.toast(message)
    }

    override fun onSuccessSearch(searchResult: CarsModel) {

        listCars=searchResult.data

        list_of_search_cars?.layoutManager= LinearLayoutManager(context)
        searchCars.isClickable=true

      //  list_of_search_cars?.adapter=context?.let { searchResultAdapter(it ,this, listCars!!) }



    }

    override fun onStartSearch() {
        context?.toast("start search")
    }

    override fun onNoteClick(position: Int) {
        context?.toast(position.toString())
        val car=listCars?.get(position)
        carId= listCars?.get(position)!!.id
        val action = searchFragmentDirections .actionSearchFragmentToCarDetails(carId)
        view?.findNavController()?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (com.mustafayusef.sharay.R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (com.mustafayusef.sharay.R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == com.mustafayusef.sharay.R.id.carDetails) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
        val last=latestCar(  `class`= car?.`class`,
         active=car?.active,
         airBags=car?.airBags,
         brand= car?.brand,
         color=car?.color,
         cylinders=car?.cylinders,
         date=car?.date,
         description=car?.description,
         driveSystem=car?.driveSystem,
         fuel=car?.fuel,
         gear=car?.gear,
         id=car?.id ,
         image=car?.image,

         location=car?.location ,
         mileage=car?.mileage ,
         name=car?.name,
         phone=car?.phone ,
         price=car?.price ,
         roof=car?.roof ,
         seats=car?.seats ,
         status=car?.status ,
         storeId=car?.storeId,
         title=car?.title ,
         type=car?.type ,
         userId=car?.userId ,
         warid=car?.warid,
         window=car?.window ,
         year=car?.year )
        viewModel.saveData(last)
        viewModel.getData()


    }
    override fun onSuccessDatabase(searchResult: List<latestCar>) {
        responseCars=searchResult
        SearchLastList?.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        println(searchResult)
        SearchLastList?.adapter= context?.let { lastSearchAdapter(it,this,searchResult!!) }
    }

    override fun OnLastLisener(position: Int) {
        carId= responseCars?.get(position)!!.id!!
        val action = searchFragmentDirections .actionSearchFragmentToCarDetails(carId)
        view?.findNavController()?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (com.mustafayusef.sharay.R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (com.mustafayusef.sharay.R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == com.mustafayusef.sharay.R.id.carDetails) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
    override fun onSuccessDatabasesave(message: String) {

        context?.toast(message)
    }
}
