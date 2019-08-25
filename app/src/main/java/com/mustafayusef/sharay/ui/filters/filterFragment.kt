package com.mustafayusef.sharay.ui.filters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.carData
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter_fragment.*
import kotlinx.android.synthetic.main.filter_store.view.applayFilter
import kotlinx.android.synthetic.main.filters_dilog1.view.*
import kotlinx.android.synthetic.main.filters_dilog1.view.closeDf
import kotlinx.android.synthetic.main.filters_dilog2.view.*

class filterFragment : Fragment(),FilterCarLesener {


    private lateinit var navController: NavController
    var responseCars:List<DataCars>?=null
    val locations= arrayListOf("بغداد","القادسية","دهوك","البصرة","بابل","الأنبار","اربيل","ذي قار","السليمانية","صلاح الدين","ديالى","كركوك","كربلاء","المثنى","ميسان","النجف","واسط","الموصل")

    val colors =arrayListOf ("ابيض","حليبي","سلفر","نيلي","سمائي","ازرق","احمر","ماروني","برتقالي","اصفر","اخضر","جوزي","قيلي","اسود","بنفسجي","وردي")
    val statusArr =arrayListOf ("جديد","جديد قطعة صبغ","جديد قطعتان صبغ","جديد ٣ قطع صبغ","جديد ٤ قطع صبغ","مستعمل","مستعمل قطعة صبغ","مستعمل قطعتان صبغ","مستعمل ٣ قطغ صبغ","مستعمل ٤ قطع صبغ","مستعمل ٥ قطع صبغ","مستعمل صبغ عام","غرقان")
//    val years =arrayListOf ("1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022")
    val sources =arrayListOf ("امريكي","خليجي","كندي","كوري","اوربي")

    val milesArr =arrayListOf ("0","10000","  20000 ","  30000 ","  40000 ","  50000 ","   60000 ","  70000 ","  80000 ","  90000 ","  100000 ","  150000 ","  200000 ","  300000 ")
    val cylindersArr = arrayListOf ("1","2","3","4","5","6","7","8","9","10","11","12")
    val gearTransmitionArr =arrayListOf ("اوتماتيك","أوتو تيبترونك","اوتوماتيك سبورت","وتوماتيك 8 سرعات","F1 اوتوماتيك", "يدوي")
    val driveSystemArr =arrayListOf ("امامي","خلفي","رباعية الدفع")
    val fuelTypeArr =arrayListOf ("بانزين","كاز","غاز","كهرباء","بانزين + غاز")
    val roofArr = arrayListOf ("توجد فتحة","لا توجد فتحة")
    val douchmahArr = arrayListOf ("جلد","قماش")
    val windowsSystemArr = arrayListOf ("كهربائي", "يدوي")


   var indBrand=0
    var selectYear:String?=null
    var selectClass:String?=null

    var selectBrand:String?=null
    var selectLoc:String?=null
    var selectMinPrice:String?=null
    var selectMaxPrice:String?=null

    var selectSource:String?=null
    var selectMile:String?=null
    var PriceList: MutableList<String> = arrayListOf()
    var years: MutableList<String> = arrayListOf()

    var indClass=-1

    companion object {
        fun newInstance() = filterFragment()
    }

    private lateinit var viewModel: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toolbar = activity?.findViewById<ImageButton> (R.id.filterBtn)
        view?.findNavController()?.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id == R.id.filterFragment) {
                // navBar?.visibility = View.GONE
                toolbar?.visibility = View.INVISIBLE

            } else {
                // navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }




        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory= FiltersCarViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this,factory).get(FilterViewModel::class.java)
        viewModel?.Auth=this

        var index=0

        for(i in 0..100000 step 1000){
            PriceList.add(index++,i.toString())
        }

        var index1=0

        for(i in 2022 downTo 1940){
            years.add(index1++,i.toString())
        }

        val suggest: Array<carData>
        var json: String = ""
        val objectArrayString: String = context?.resources?.openRawResource(R.raw.cars)?.bufferedReader()
            .use { it!!.readText() }
        val gson = Gson()
        suggest = gson.fromJson(objectArrayString, Array<carData>::class.java)
        var names = mutableListOf("")
        for (i in suggest) {
            names.add(i.name)
        }
       // println(PriceList)

        applayFilters.setOnClickListener {


            viewModel.GetFiltersCars(selectBrand,selectClass,selectYear,selectSource,selectMile,selectMinPrice,selectMaxPrice,selectLoc)

        println("selectYear  "+selectYear)
        }
        closeFilter.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        yearDilog.setOnClickListener {
            showYear()
        }
        carClassDilog.setOnClickListener {

            showClass(names)
        }
        carModelDilog.setOnClickListener {
            if(indClass==-1){
                carClassDilog.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
               // classFilter.setHintTextColor(-0x01ffff)

            }else{
                showBrand(suggest[indClass].data as ArrayList<String>)

            }
        }

        locationDilog.setOnClickListener {
            showlocation()
        }
        priceDilog.setOnClickListener {
            showPrice()
        }
        sourceDilog.setOnClickListener {
            showSource()
        }
        milesDilog.setOnClickListener {
            showMiles()
        }


    }
    override fun OnStart() {
        animation_loadingFilter?.visibility=View.VISIBLE
        animation_loadingFilter?.playAnimation()
    }

    override fun onSucsess(CarResponse: CarsModel) {
        animation_loadingFilter?.visibility=View.GONE

      val cars=CarResponse
        var bundle = bundleOf("Cars" to cars)
      view?.findNavController()?.navigate(R.id.action_filterFragment_to_showResult,bundle)
        //animation_loadingMain.pauseAnimation()

    }

    override fun onFailer(message: String) {
        animation_loadingFilter?.visibility=View.GONE
        context?.toast(resources.getString(R.string.problemCon))
    }



    fun showYear() {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = years.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = years.toTypedArray()
        dview.filterTitle.text=resources.getString(R.string.Year)
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {
            selectYear = years[index]
            typeFilter.text=selectYear
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }
    }

    fun showClass(names: MutableList<String>) {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = names.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = names.toTypedArray()
        dview.filterTitle.text=resources.getString(R.string.CarClass)
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {
            indClass=index-1
            selectClass = names[index]
            classFilter.text=selectClass

            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }

    }
    fun showBrand(data: List<String>) {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = data.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = data.toTypedArray()
        dview.filterTitle.text=resources.getString(R.string.CarModel)
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {

            indBrand=index-1
            carModelFilter.text=data[index]
            selectBrand=data[index]
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }
    }

    fun showlocation() {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        dview.filterTitle.text="Location"
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = locations.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = locations.toTypedArray()
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {
            selectLoc = locations[index]
            locationFilter.text=selectLoc
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }
    }
    fun showPrice() {

        val dview: View = layoutInflater.inflate(R.layout.filters_dilog2, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert = builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.min.minValue = 0
        dview.max.minValue = 0
        dview.min.maxValue = PriceList.size - 1
        dview.max.maxValue = PriceList.size - 1
        dview.min.wrapSelectorWheel = true
        dview.max.wrapSelectorWheel = true
        dview.min.displayedValues = PriceList.toTypedArray()
        dview.max.displayedValues = PriceList.toTypedArray()
        var index1 = 0
        var index2 = 0
        dview.min.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index1 = newVal
            // println(country +"   cooodkl,dl")
        }
            dview.max.setOnValueChangedListener { picker, oldVal, newVal ->
                //
                //Display the newly selected number to text view
                index2 = newVal
                // println(country +"   cooodkl,dl")
            }
            dview.applayFilterPrice.setOnClickListener {
                selectMinPrice = PriceList[index1]
                selectMaxPrice = PriceList[index2]
                priceFilter.text = "from $selectMinPrice to $selectMaxPrice "
                malert?.dismiss()
            }
            dview.closePriceF.setOnClickListener {
                malert?.dismiss()
            }

    }

    fun showSource() {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = sources.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = sources.toTypedArray()
        dview.filterTitle.text="Source"
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {
            selectSource = sources[index]
            sourceFilter.text=selectSource
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }
    }

    fun showMiles() {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = milesArr.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = milesArr.toTypedArray()
        var index=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {
            selectMile = milesArr[index]
            mileFilter.text=selectMile
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
        }
    }
}
