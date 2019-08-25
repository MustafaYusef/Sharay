package com.mustafayusef.sharay.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
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
import com.mustafayusef.sharay.data.models.DataCarDetails
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
import kotlinx.android.synthetic.main.show_result_fragment.*


class searchFragment : Fragment(),lastSearchAdapter.OnLastLisener,searchLesener,searchResultAdapter.OnNoteLisener {



    var carId:Int = 0
    var responseCars:List<latestCar>?=null
    var listCars:List<DataCars>?=null
    var searchCar:List<DataCars>?=null
    var Suggest:MutableList<String> = arrayListOf()
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


        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.searchFragment) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE
            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }

        }

        searchCars?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // do something on text submit
                filter(query.toString())
                up()
                if(temp.isNullOrEmpty()){
                    noNetContainerSearch?.visibility=View.VISIBLE

                }else{
                    noNetContainerSearch?.visibility=View.GONE
                }
                lastContainer?.visibility=View.GONE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if(!newText!!.isEmpty()){
                     filter(newText.toString())
                     up()
                     if(temp.isNullOrEmpty()){
                       //  noNetContainerSearch?.visibility=View.VISIBLE
                         lastContainer?.visibility=View.VISIBLE

                     }else{
                         noNetContainerSearch?.visibility=View.GONE
                         lastContainer?.visibility=View.GONE
                     }
                 }else{
                     filter(newText.toString())
                     up()
                     lastContainer?.visibility=View.VISIBLE
                     noNetContainerSearch?.visibility=View.GONE
                 }

                return false
            }
        })


    }
    fun up(){
      //  list_of_search_cars?.layoutManager= LinearLayoutManager(context)
        list_of_search_cars.adapter=context?.let { searchResultAdapter(it ,this, temp!!) }

    }
    fun filter(text:String){
       // context?.toast("start filter")
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
//        for(i in 0 until  listCars!!.size){
//            Suggest.add(i, listCars!!.get(i).title)
//        }
        list_of_search_cars?.layoutManager= LinearLayoutManager(context)
        searchCars.isClickable=true

      //  list_of_search_cars?.adapter=context?.let { searchResultAdapter(it ,this, listCars!!) }



    }

    override fun onStartSearch() {
        context?.toast("start search")
    }

    override fun onNoteClick(position: Int) {
        context?.toast(position.toString())
      //  val car=listCars?.get(position)
        carId= temp?.get(position)!!.id
        var last=latestCar(
            id=temp?.get(position)!!.id ,
            image=temp?.get(position)!!.image,
            title=temp?.get(position)!!.title
        )
//        var last=latestCar(`class`= listCars?.get(position)!!.`class`,
//            active=listCars?.get(position)!!.active,
//            airBags=listCars?.get(position)!!.airBags,
//            brand= listCars?.get(position)!!.brand,
//            color=listCars?.get(position)!!.color,
//            cylinders=listCars?.get(position)!!.cylinders,
//            date=listCars?.get(position)!!.date,
//            description=listCars?.get(position)!!.description,
//            driveSystem=listCars?.get(position)!!.driveSystem,
//            fuel=listCars?.get(position)!!.fuel,
//            gear=listCars?.get(position)!!.gear,
//            id=listCars?.get(position)!!.id ,
//            image=listCars?.get(position)!!.image,
//
//            location=listCars?.get(position)!!.location ,
//            mileage=listCars?.get(position)!!.mileage ,
//            name=listCars?.get(position)!!.name,
//            phone=listCars?.get(position)!!.phone ,
//            price=listCars?.get(position)!!.price ,
//            roof=listCars?.get(position)!!.roof ,
//            seats=listCars?.get(position)!!.seats ,
//            status=listCars?.get(position)!!.status ,
//            storeId=listCars?.get(position)!!.storeId,
//            title=listCars?.get(position)!!.title ,
//            type=listCars?.get(position)!!.type ,
//            userId=listCars?.get(position)!!.userId ,
//            warid=listCars?.get(position)!!.warid,
//            window=listCars?.get(position)!!.window ,
//            year=listCars?.get(position)!!.year )
        viewModel.saveData(last)
        viewModel.getData()
                val action = searchFragmentDirections .actionSearchFragmentToCarDetails(carId)
        view?.findNavController()?.navigate(action)
//        val navBar = activity?.findViewById<BottomNavigationView> (com.mustafayusef.sharay.R.id.bottomNav)
//        val toolbar = activity?.findViewById<Toolbar> (com.mustafayusef.sharay.R.id.ToolBar)
//
//        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == com.mustafayusef.sharay.R.id.carDetails) {
//                navBar?.visibility = View.GONE
//                toolbar?.visibility = View.GONE
//
//            } else {
//                navBar?.visibility = View.VISIBLE
//                toolbar?.visibility = View.VISIBLE
            }


    override fun onSuccessDatabase(searchResult: List<latestCar>) {
        responseCars=searchResult
        SearchLastList?.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        println(searchResult)
        SearchLastList?.adapter= context?.let { lastSearchAdapter(it,this,searchResult!!) }
    }

    override fun OnLastLisener(position: Int) {
//        carId= responseCars?.get(position)!!.id!!
//
//
//
//
//        val action = searchFragmentDirections .actionSearchFragmentToCarDetails(carId)
//        view?.findNavController()?.navigate(action)
//        val navBar = activity?.findViewById<BottomNavigationView> (com.mustafayusef.sharay.R.id.bottomNav)
//        val toolbar = activity?.findViewById<Toolbar> (com.mustafayusef.sharay.R.id.ToolBar)
//
//        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == com.mustafayusef.sharay.R.id.carDetails) {
//                navBar?.visibility = View.GONE
//                toolbar?.visibility = View.GONE
//
//            } else {
//                navBar?.visibility = View.VISIBLE
//                toolbar?.visibility = View.VISIBLE
//            }
//        }
    }
    override fun onSuccessDatabasesave(message: String) {

        //context?.toast(message)
    }
    override fun onStartDetails() {

    }

    override fun onSuccessDetails(dataCarDetails: DataCarDetails) {


    }

    override fun onFailerDetails(message: String) {
        context?.toast(message)
    }
}
