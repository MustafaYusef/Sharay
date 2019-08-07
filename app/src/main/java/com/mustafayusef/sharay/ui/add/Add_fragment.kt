package com.mustafayusef.sharay.ui.add

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.add_fragment_fragment.*
import kotlinx.android.synthetic.main.car_details_fragment.*

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.FileUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.carData
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.filters.FilterViewModel
import com.mustafayusef.sharay.ui.filters.FiltersCarViewModelFactory
import kotlinx.android.synthetic.main.filter_fragment.*
import kotlinx.android.synthetic.main.filter_store.view.*
import kotlinx.android.synthetic.main.filters_dilog1.view.*
import kotlinx.android.synthetic.main.filters_dilog1.view.applayFilter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class Add_fragment : Fragment(),PictureAdapter.OnNoteLisener,AddCarLesener {
    var title: String?=null
    var brand: String?=null
    var `class`: String?=null
    var status: String?=null
    var year: Int?=null
    var warid: String?=null
    var mileage: Int?=null
    var price: String?=null
    var gear: String?=null
    var cylinders: Int?=null
    var driveSystem: String?=null
    var roof: String?=null
    var seats: Int?=null
    var type: String?=null
    var window: String?=null
    var airBags: String?=null
    var color: String?=null
    var description: String?=null
    var name: String?=null
    var phone: String?=null
    var location: String?=null
    var state: String?=null
    var date: String?=null
    var userId: Int?=null
    var storeId: Int?=null
    var active: Boolean?=null
    var fuel:String?=null
    var isRent: Boolean?=null
    var isImported: Boolean?=null
    var image: MultipartBody.Part?=null
  //  var filePart:MultipartBody.Part
    var responseCars:List<DataCars>?=null
    val locations= arrayListOf("بغداد","القادسية","دهوك","حلبجة","البصرة","بابل","انبار","اربيل","ذي قار","السليمانية","صلاح الدين","ديالى","كركوك","كربلاء","المثنى","ميسان","النجف","نينوى","واسط","الموصل")

    val colors =arrayListOf ("ابيض","حليبي","سلفر","نيلي","سمائي","ازرق","احمر","ماروني","برتقالي","اصفر","اخضر","جوزي","قيلي","اسود","بنفسجي","وردي")
    val statusArr =arrayListOf ("جديد","جديد قطعة صبغ","جديد قطعتان صبغ","جديد ٣ قطع صبغ","جديد ٤ قطع صبغ","مستعمل","مستعمل قطعة صبغ","مستعمل قطعتان صبغ","مستعمل ٣ قطغ صبغ","مستعمل ٤ قطع صبغ","مستعمل ٥ قطع صبغ","مستعمل صبغ عام","غرقان")
    val years =arrayListOf ("1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022")
    val sources =arrayListOf ("امريكي","خليجي","كندي","كوري","اوربي")

    val milesArr =arrayListOf ("0","10000","  20000 ","  30000 ","  40000 ","  50000 ","   60000 ","  70000 ","  80000 ","  90000 ","  100000 ","  150000 ","  200000 ","  300000 ")
    val cylindersArr = arrayListOf ("1","2","3","4","5","6","7","8","9","10","11","12")
    val gearTransmitionArr =arrayListOf ("اوتماتيك","أوتو تيبترونك","اوتوماتيك سبورت","وتوماتيك 8 سرعات","F1 اوتوماتيك", "يدوي")
    val driveSystemArr =arrayListOf ("امامي","خلفي","رباعية الدفع")
    val fuelTypeArr =arrayListOf ("بانزين","كاز","غاز","كهرباء","بانزين + غاز")
    val roofArr = arrayListOf ("توجد فتحة","لا توجد فتحة")
    val douchmahArr = arrayListOf ("جلد","قماش")
    val windowsSystemArr = arrayListOf ("كهربائي", "يدوي")

//
//
//    var selectYear:String?=null
//    var selectClass:String?=null
//
//    var selectBrand:String?=null
//    var selectLoc:String?=null
//    var selectMinPrice:String?=null
//    var selectMaxPrice:String?=null
//
//    var selectSource:String?=null
//    var selectMile:String?=null
    var imageFile:RequestBody?=null
   lateinit var carsNames:List<String>
    private lateinit var navController: NavController
    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<Uri> = arrayListOf()
  var ind=0
    var indClass=0
    var indLoc=0
    companion object {
        fun newInstance() = Add_fragment()
    }

    private lateinit var viewModel: AddFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (MainActivity.cacheObj.language != "") {
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language) }

        }
        return inflater.inflate(com.mustafayusef.sharay.R.layout.add_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory= AddCarFactory(repostary)


        viewModel = ViewModelProviders.of(this,factory).get(AddFragmentViewModel::class.java)
        viewModel?.Auth=this
        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.add_fragment) {

                if(MainActivity.cacheObj.token=="")
                    view?.findNavController()?.navigate(R.id.fromAddToLogin)
            }
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

        val location1: Array<carData>
        var json1: String = ""
        val objectArrayString1: String = context?.resources?.openRawResource(R.raw.state)?.bufferedReader()
            .use { it!!.readText() }
        val gson1 = Gson()
        location1 = gson1.fromJson(objectArrayString1, Array<carData>::class.java)
        var names1 = mutableListOf("")
        for (i in location1) {
            names1.add(i.name)
        }
        provincAdd.setOnClickListener {
            indLoc= showMiles(names1 as ArrayList<String>)
            location=names1[indLoc]

            ind= showMiles(location1[indLoc].data as ArrayList<String>)
            state=suggest[indLoc].data[ind]

//                ind=showMiles(locations)
//                state=status[ind].toString()
        }

        BtnAdd.setOnClickListener {
            viewModel.AddCar( title!!, brand!!, `class`!!, status!!,
                year!!, warid!!, mileage!!, price!!.toInt(), gear!!, cylinders!!, fuel!!, driveSystem!!,
                roof!!, seats!!, type!!, window!!, airBags!!, color!!, description!!,
                name!!, phone!!, location!!, state!!, date!!,userId!!,
                storeId!!, active!!, isRent!!, isImported!!, image!!)
        }


            CarClassAdd.setOnClickListener {
                indClass= showMiles(names as ArrayList<String>)
                `class`=names[ind]
            }
            carModelAdd.setOnClickListener {
                ind= showMiles(suggest[indClass].data as ArrayList<String>)
                brand=suggest[indClass].data[ind]
            }
            ColorAdd.setOnClickListener {
                ind= showMiles(colors)
                color=colors[ind]
            }
            statusAdd.setOnClickListener {
                ind= showMiles(statusArr)
                status=statusArr[ind]
            }
            YearAdd.setOnClickListener {
                ind= showMiles(years)
                year=years[ind].toInt()
            }
            sourceAdd.setOnClickListener {
                ind= showMiles(sources)
                warid=sources[ind].toString()
            }
            MilesAdd.setOnClickListener {
                ind=showMiles(milesArr)
                mileage=milesArr[ind].toInt()
            }
            cylenderAdd.setOnClickListener {
                ind=showMiles(cylindersArr)
                cylinders=cylindersArr[ind].toInt()
            }
            GearAdd.setOnClickListener {
                ind=showMiles(driveSystemArr)
                driveSystem=driveSystemArr[ind]
            }
            TransmissionTypeAdd.setOnClickListener {
                ind=showMiles(gearTransmitionArr)
                gear=gearTransmitionArr[ind].toString()
            }
            FuelTypeAdd.setOnClickListener {
                ind=showMiles(fuelTypeArr)
                fuel=fuelTypeArr[ind]
            }
            RoofAdd.setOnClickListener {
                ind=showMiles(roofArr)
                roof=roofArr[ind].toString()
            }
            DouchmhAdd.setOnClickListener {
                ind=showMiles(douchmahArr)
                type=douchmahArr[ind].toString()
            }

            WindowAdd.setOnClickListener {
                ind=showMiles(windowsSystemArr)
                window=windowsSystemArr[ind].toString()
            }


            title=MainTitleAdd.text.toString()



            price=PriceAdd.text.toString()


            seats=SeatAdd.text.toString().toInt()


            airBags=airBagAdd.text.toString()

            description=DescAdd.text.toString()
            name=NameAdd.text.toString()
            phone= phoneNumAdd.text.toString()

            date=Date().toString()
            userId=MainActivity.cacheObj.id
            storeId=0
            active=false
            isRent=false
            isImported=false


        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navController= Navigation.findNavController(view)

        addedPictureList?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)


//        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,
//            listOf(resources.getString(R.string.sections), resources.getString(R.string.Carforsale)
//                ,resources.getString(R.string.carparts),resources.getString(R.string.motorcycle),
//            resources.getString(R.string.carrent),resources.getString(R.string.carcompany),
//                resources.getString(R.string.carnumber),resources.getString(R.string.importcar)))
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)






        openGallary.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

     }
    override fun OnStart() {

    }

    override fun onSucsess(CarResponse: addRes) {

    }

    override fun onFailer(message: String) {

    }
    override fun onNoteClick(position: Int) {
        Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
        imagesPathList.removeAt(position)
        addedPictureList?.adapter = context?.let {
            PictureAdapter(
                it, this,
                imagesPathList.toTypedArray()
            )
        }
    }
    fun showMiles(array:ArrayList<String>):Int {
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = array.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = array.toTypedArray()
        var index=0

        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {

            //mileFilter.text=selectMile
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }
        return index
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // something is wrong
                }

                val clipData = data?.clipData
                if (clipData != null) { // handle multiple photo
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        imagesPathList.add(i, uri)

                        addedPictureList?.adapter = context?.let {
                            PictureAdapter(
                                it, this,
                                imagesPathList.toTypedArray()
                            )
                        }
                        var oregnal:File=imagesPathList.get(0).toFile()
                        imageFile= RequestBody.create(
                        MediaType.parse(context?.contentResolver?.getType(imagesPathList.get(0))),
                            oregnal)

                        image=MultipartBody.Part.createFormData("image",
                           oregnal.name,imageFile)


                    }
                } else { // handle single photo
                    val uri: Uri? = data?.data
                    uri?.let { imagesPathList.add(0, it) }
                    addedPictureList?.adapter = context?.let {
                        PictureAdapter(
                            it, this,
                            imagesPathList.toTypedArray()
                        )
                    }
                    var oregnal:File=imagesPathList.get(0).toFile()
                    imageFile= RequestBody.create(
                        MediaType.parse(context?.contentResolver?.getType(imagesPathList.get(0))),
                        oregnal)

                    image=MultipartBody.Part.createFormData("image",
                        oregnal.name,imageFile)
                }
            }
        }

    }
}
