package com.mustafayusef.sharay.ui.sections.carrent

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mustafayusef.holidaymaster.networks.networkIntercepter

import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.public_fragment.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.sections.*
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import com.mustafayusef.sharay.ui.sections.adapters.*
import kotlinx.android.synthetic.main.desc_parts.view.*
import kotlinx.android.synthetic.main.filter_store.view.*


class CareRent : Fragment()
    ,rentLesener,partsAdapter.OnPartLisener,
    CarSaleAdapter.OnNoteLisener,storesAdapter.onStoreClick
    ,RentAdapter.OnRentLisener,MotorAdapter.OnMotorLisener{



    var carId:Int = 0
    var responseCars:List<DataCars>?=null
    var partResponse:List<partsData>?=null
    var MotorResponse:List<motorData>?=null
    var RentResponse:List<CarRent>?=null

    var stores:List<StoresData>?=null
    var temp:MutableList<StoresData> = arrayListOf()
    val numbers: IntArray = intArrayOf(10, 20, 30, 40, 50)
   val locations= arrayListOf("الكل","بغداد","القادسية","دهوك","البصرة","بابل","الأنبار","اربيل","ذي قار","السليمانية","صلاح الدين","ديالى","كركوك","كربلاء","المثنى","ميسان","النجف","واسط","الموصل")
    var type:String?=null
    var selectLoc:String=locations[0]
    private lateinit var navController: NavController
    companion object {
        fun newInstance() = CareRent()
    }

    private lateinit var viewModel: CareRentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(com.mustafayusef.sharay.R.layout.public_fragment, container, false)
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        filterBtnP.setOnClickListener {
            showFilter()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= SectionRepostary(api!!)
        val factory= CarRentFactory(repostary)
        viewModel = ViewModelProviders.of(activity!!,factory).get(CareRentViewModel::class.java)


        viewModel.lesener =this
        type = arguments?.getString("type")
        retryBtnPublic.setOnClickListener {
            when(type){
                "numbers"-> {
                    viewModel.GetNumbers()
                    titlePublic.text = resources.getString(R.string.carnumber)
                }
                "stores"-> {
                    viewModel.GetStores()
                    titlePublic.text = resources.getString(R.string.carcompany)
                }
                "rent"->{viewModel.GetRent()
                    titlePublic.text=resources.getString(R.string.carrent)
                }
                "parts"->{viewModel.GetParts()
                    titlePublic.text=resources.getString(R.string.carparts)
                }
                "import"->{viewModel.GetImport()
                    titlePublic?.text=resources.getString(R.string.importcar)}
                "motore"->{viewModel.GetMotor()
                    titlePublic?.text=resources.getString(R.string.motorcycle)}

                "cars"->{viewModel.GetCars()
                    titlePublic.text=resources.getString(R.string.Carforsale)
                }

            }
        }
        when(type){
            "numbers"-> {
                viewModel.GetNumbers()
                titlePublic.text = resources.getString(R.string.carnumber)
            }
                "stores"-> {
                viewModel.GetStores()
                titlePublic.text = resources.getString(R.string.carcompany)
            }
                "rent"->{viewModel.GetRent()
                titlePublic.text=resources.getString(R.string.carrent)
            }
            "parts"->{viewModel.GetParts()
                titlePublic.text=resources.getString(R.string.carparts)
            }
            "import"->{viewModel.GetImport()
            titlePublic?.text=resources.getString(R.string.importcar)}
            "motore"->{viewModel.GetMotor()
                    titlePublic?.text=resources.getString(R.string.motorcycle)}

            "cars"->{viewModel.GetCars()
            titlePublic.text=resources.getString(R.string.Carforsale)
        }

    }

        //val navBar = activity?.findViewById<BottomNavigationView> (com.mustafayusef.sharay.R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (com.mustafayusef.sharay.R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == com.mustafayusef.sharay.R.id.careRent) {
             //   navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
              //  navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
    override fun OnStart() {
        noNetContainerPublic?.visibility=View.GONE
        animation_loadingSections?.visibility=View.VISIBLE
    }

    override fun onSucsessNumbers(SectionsResponse: NumbersModel) {
        titlePublic?.text=resources.getString(R.string.carnumber)
        animation_loadingSections?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        if(SectionsResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        var rev=SectionsResponse.data!!.asReversed()
        sectionsList?.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
        sectionsList?.adapter= context?.let { numberAdapter(it , rev)}
        }

    override fun onSucsessRent(CarResponse: RentModel) {
        RentResponse=CarResponse.data
        animation_loadingSections?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        var rev=CarResponse.data!!.asReversed()
        sectionsList?.layoutManager = LinearLayoutManager(context)
        sectionsList?.adapter= context?.let { rev?.let { it1 -> RentAdapter(it , it1,this) } }    }

    override fun onSucsessStores(CarResponse: StoresModel) {
        stores=CarResponse.data
        titlePublic?.text=resources.getString(R.string.carcompany)
        filterBtnP?.visibility=View.VISIBLE
        animation_loadingSections?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
       // CarResponse.data[0].location
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        sectionsList?.layoutManager= LinearLayoutManager(context)
        sectionsList?.adapter= context?.let { storesAdapter(it , CarResponse.data,this)}
    }

    override fun onSucsessCars(CarResponse: CarsModel) {
        animation_loadingSections?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        var rev=CarResponse.data!!.asReversed()
        sectionsList?.layoutManager= LinearLayoutManager(context)
       sectionsList?.adapter= context?.let { CarSaleAdapter(it, this,rev) }
        responseCars=CarResponse.data
    }

    override fun onSucsessParts(CarResponse: PartsModel) {
        partResponse=CarResponse.data
        titlePublic?.text=resources.getString(R.string.carparts)
        animation_loadingSections?.visibility=View.GONE
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        //animation_loadingMain.pauseAnimation()
        var rev=CarResponse.data!!.asReversed()
        sectionsList?.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
        sectionsList?.adapter= context?.let { partsAdapter(it , rev,this)}
    }
    override fun onSucsessMotor(CarResponse: motorModel) {
        MotorResponse=CarResponse.data
        titlePublic?.text=resources.getString(R.string.motorcycle)
        animation_loadingSections?.visibility=View.GONE
        //animation_loadingMain.pauseAnimation()
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        var rev=CarResponse.data!!.asReversed()
        sectionsList?.layoutManager = LinearLayoutManager(context)
        sectionsList?.adapter= context?.let { MotorAdapter(it ,this, rev)}
//        sectionsList?.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
//       sectionsList?.adapter= context?.let { MotorAdapter(it ,this, CarResponse.data)}
    }
    override fun onSucsessImport(CarResponse: RentModel) {

    RentResponse=CarResponse.data

    animation_loadingSections?.visibility=View.GONE
    //animation_loadingMain.pauseAnimation()
        if(CarResponse.data.isNullOrEmpty()){
            noResultSections?.visibility=View.VISIBLE
        }
        var rev=CarResponse.data!!.asReversed()
    sectionsList?.layoutManager = LinearLayoutManager(context)
    sectionsList?.adapter= context?.let { rev?.let { it1 -> RentAdapter(it , it1,this) } }    }

override fun OnFailer(message: String) {
    noNetContainerPublic?.visibility=View.VISIBLE
    animation_loadingSections?.visibility=View.GONE
   }

    override fun onNoteClick(position: Int) {
        carId= responseCars?.get(position)!!.id
        val action = CareRentDirections.actionCareRentToCarDetails(carId)
        navController?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
    override fun OnPartLisener(position: Int) {
        val dview: View = layoutInflater.inflate(R.layout.desc_parts, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.descPart?.text=partResponse?.get(position)?.description

    }
    override fun onStoreClick(position: Int) {
       var id= stores?.get(position)!!.id
        var bundle = bundleOf("id" to id)
        view?.findNavController()?.navigate(R.id.rentToStoreDetails, bundle)
    }
    override fun OnRentLisener(position: Int) {
        carId= RentResponse?.get(position)!!.id
        val action = CareRentDirections.actionCareRentToCarDetails(carId)
        navController?.navigate(action)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
    override fun OnMotorLisener(position: Int) {
        var id= MotorResponse?.get(position)!!.id
        var bundle = bundleOf("id" to id)
        view?.findNavController()?.navigate(R.id.fromCarRentToMotorDetails, bundle)
    }
    fun showFilter() {

        val dview: View = layoutInflater.inflate(R.layout.filter_store, null)

        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }

        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.provId.minValue = 0
        dview.provId.maxValue = locations.size-1
        dview.provId.wrapSelectorWheel = true
        dview.provId.displayedValues = locations.toTypedArray()
        var index=0
        dview.provId.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal

            // println(country +"   cooodkl,dl")
        }
//            }

        dview.applayFilter.setOnClickListener {
            applayFilter(locations[index])
            malert?.dismiss()

            // national.clear()
        }
        dview.closeFilter.setOnClickListener {
            malert?.dismiss()
        }
    }

    fun applayFilter(text:String){

        temp.clear()
        if(locations.indexOf(text)==0){

            sectionsList?.layoutManager= LinearLayoutManager(context)
            sectionsList?.adapter= context?.let { storesAdapter(it , stores!!,this)}
        }else{
            for(te in this!!.stores!!){
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches

                if(te.location .contains(text)){
                    temp.add(te)
                }
            }
            sectionsList?.layoutManager= LinearLayoutManager(context)
            sectionsList?.adapter= context?.let { storesAdapter(it , temp,this)}
        }


    }
}