package com.mustafayusef.sharay.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_STRING
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi


import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.add_fragment_fragment.*

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.carData
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.auth.signup.Login
import com.mustafayusef.sharay.ui.profile.Profile_fragment

import kotlinx.android.synthetic.main.filters_dilog1.view.*
import kotlinx.android.synthetic.main.filters_dilog1.view.applayFilter
import kotlinx.android.synthetic.main.info.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedReader

import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

import kotlin.collections.ArrayList


class Add_fragment : Fragment(),PictureAdapter.OnNoteLisener,AddCarLesener {


    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123
    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<String> = arrayListOf()

    var title: String?=null
    var brand: String?=null
    var `class`: String?=null
    var status: String?=null
    var year: Int?=null
    var warid: String?=null
    var mileage: Int?=null
    var price: Int?=null
    var gear: String?=null
    var cylinders: Int?=null
    var driveSystem: String?=null
    var roof: String?=null
    var seats: Int=1
    var type: String?=null
    var window: String=" "
    var airBags: String=" "
    var color: String?=null
    var description: String=" "
    var name=MainActivity.cacheObj  .name
    var phone: String?=null
    var location: String?=null
    var state: String?=null
    var date: String?=null
    var userId: Int=MainActivity.cacheObj .id
    var storeId: Int=0
    var active: Boolean=true
    var fuel:String=" 1"
    var isRent: Boolean=false
    var isImported: Boolean=false
    var image: MultipartBody.Part?=null
    var imagesBodyList: MutableList<MultipartBody.Part> = arrayListOf()
    var imagesBodyList2: MutableList<MultipartBody.Part> = arrayListOf()
    var navBar:BottomNavigationView?=null
    var toolbar:Toolbar?=null
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

//    var PICK_IMAGE_MULTIPLE = 1
//    lateinit var imagePath: String
    var imagesPathListUri: MutableList<Uri> = arrayListOf()
  var ind=0
    var indClass=-1
    var indBrand=0
    var indLoc=-1
var loc2:List<carData>?=null

    var keyFlag:Boolean=false
//    var easyImage= EasyImage.Builder(context!!)
//
//
//// Chooser only
//// Will appear as a system chooser title, DEFAULT empty string
////.setChooserTitle("Pick media")
//// Will tell chooser that it should show documents or gallery apps
////.setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)  you can use this or the one below
////.setChooserType(ChooserType.CAMERA_AND_GALLERY)
//
//// Setting to true will cause taken pictures to show up in the device gallery, DEFAULT false
//        .setCopyImagesToPublicGalleryFolder(false)
//// Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
//        .setFolderName("EasyImage sample")
//
//// Allow multiple picking
//        .allowMultiple(true)
//        .build();
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

                if(MainActivity.cacheObj  .token=="")
                    view?.findNavController()?.navigate(R.id.fromAddToLogin)
            }
        }
        val suggest: Array<carData>

        val objectArrayString: String = context?.resources?.openRawResource(R.raw.cars)?.bufferedReader()
            .use { it!!.readText() }
        val gson = Gson()
        suggest = gson.fromJson(objectArrayString, Array<carData>::class.java)
        var names = mutableListOf("")
        for (i in suggest) {
            names.add(i.name)
        }

        CarClassAdd.setOnClickListener {
            showClass(names as ArrayList<String>,CarClassAdd)
        }
        carModelAdd.setOnClickListener {
            if(indClass==-1){
                CarClassAdd.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
               // CarClassAdd.setTextColor(-0x01ffff)
            }else{
              showBrand(suggest[indClass].data as ArrayList<String>, carModelAdd)
                brand=suggest[indClass].data[indBrand]
            }
        }

        var location1: Array<carData>
        var locnames = mutableListOf("")
        val objectArrayString1: String = context?.resources?.openRawResource(R.raw.state)?.bufferedReader()
            .use { it!!.readText() }
        val gson1 = Gson()
        location1 = gson1.fromJson(objectArrayString1, Array<carData>::class.java)
        loc2=location1.toList()
        var provinc = mutableListOf("")
        for (i in location1) {
            locnames.add(i.name)

        }
println(locnames)
        provincAdd.setOnClickListener {
            showLoc(locnames as ArrayList<String>, CarClassAdd)
        }
         navBar= activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
         toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        BtnAdd.setOnClickListener {


            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            date=currentDate
            if(brand==null||`class`==null||status==null
                ||year==null||warid==null||mileage==null||gear==null||cylinders==null||
                fuel==null||driveSystem==null||roof==null||seats==null||type==null||color==null
                ||location==null
                ||state==null||imagesBodyList.size==0||MainTitleAdd.text.toString()==""
                ||PriceAdd.text.toString()==""||phoneNumAdd.text.toString()==""){
                  context?.toast(getResources().getString(R.string.complete))
            }else if(phoneNumAdd.text.toString().length<=10&&phoneNumAdd.text.toString().length<16){
                context?.toast(getResources().getString(R.string.EnterPhoneCorrect))
            }else if(imagesBodyList.size>12){
                context?.toast(getResources().getString(R.string.maximum))

            }
            else{
                title= MainTitleAdd.text.toString()
                price=PriceAdd.text.toString().toInt()
                phone=phoneNumAdd.text.toString()
                navBar?.isClickable=false
                toolbar?.isClickable=false


                viewModel.AddCar( title!!, brand!!, `class`!!, status!!,
                    year!!, warid!!, mileage!!, price!!, gear!!, cylinders!!, fuel!!, driveSystem!!,
                    roof!!, seats!!, type!!, window!!, airBags!!, color!!, description!!,
                    name!!, phone!!, location!!, state!!, date!!,userId!!,
                    storeId!!, active!!, isRent!!, isImported!!, imagesBodyList.get(0)!!)
            }

        }



            ColorAdd.setOnClickListener {
               Reuseable(colors,ColorAdd)


            }
            statusAdd.setOnClickListener {
                Reuseable(statusArr,statusAdd)

            }
            YearAdd.setOnClickListener {
                Reuseable(years,YearAdd)

            }
            sourceAdd.setOnClickListener {

                Reuseable(statusArr,sourceAdd)

            }
            MilesAdd.setOnClickListener {

                Reuseable(milesArr,MilesAdd)
            }
            cylenderAdd.setOnClickListener {

                Reuseable(cylindersArr,cylenderAdd)

            }
            GearAdd.setOnClickListener {

                Reuseable(driveSystemArr,GearAdd)
            }
            TransmissionTypeAdd.setOnClickListener {

                Reuseable(gearTransmitionArr,TransmissionTypeAdd)
            }
            FuelTypeAdd.setOnClickListener {
                Reuseable(fuelTypeArr,FuelTypeAdd)
            }
            RoofAdd.setOnClickListener {
                Reuseable(roofArr,RoofAdd)
            }
            DouchmhAdd.setOnClickListener {
                Reuseable(douchmahArr,DouchmhAdd)

            }





        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navController= Navigation.findNavController(view)
        val dview: View = layoutInflater.inflate(R.layout.info, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()

        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dview.info?.text=
            "\n" +
                    "اعلان لدنيا\n" +
                    "لاعلان سيارتكم انقر على (اضافة اعلان)،اتبع الخطوات بملئ الحقول جميعها من السعر (ينصح بكتابة السعر) وتحميل الصور على ان تكون صور في اضاءة جيدة و اختيار زوايا مناسبة لجوانب السيارة الاربعة كاملة دون اقتطاع ، مع صورتين لداخلية السيارة.\n" +
                    "ستصلكم رسالة نصية على جوالكم عند نشر الاعلان\n" +
                    "\n" +
                    "للاستفسار او للمساعدة الفنية اتصل على \n" +
                    "0781 000 6405\n" +
                    "0771 460 1419\n" +
                    "Central.marketiq@gmail.com"

        dview.goLog?.setOnClickListener {
            malert?.dismiss()
        }
        addedPictureList?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)


//        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,
//            listOf(resources.getString(R.string.sections), resources.getString(R.string.Carforsale)
//                ,resources.getString(R.string.carparts),resources.getString(R.string.motorcycle),
//            resources.getString(R.string.carrent),resources.getString(R.string.carcompany),
//                resources.getString(R.string.carnumber),resources.getString(R.string.importcar)))
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


    // if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.READ_EXTERNAL_STORAGE))


//        Intent.ACTION_PICK,
//        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

//        openGallary.setOnClickListener {
//
//           // val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
//            intent.type = "image/*"
//           // intent.action=Intent.ACTION_GET_CONTENT
//            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
//
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
//
//        }
// Here, thisActivity is the current activity

//        if (ContextCompat.checkSelfPermission(context!!,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity?.parent!!,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(activity?.parent!!,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    100)
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//            // Permission has already been granted
//        }
        openGallary.setOnClickListener {
           if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture")
                    , PICK_IMAGE_MULTIPLE
                )


            } else {
            if (Build.VERSION.SDK_INT >=23) {
                if(checkPermissionREAD_EXTERNAL_STORAGE(context!!)){
                    var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action= Intent.ACTION_GET_CONTENT
                   // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
                }else{
                    context?.toast("you can not pick images")
                }
            }else{
              //  var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                var intent = Intent()
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action= Intent.ACTION_GET_CONTENT
               intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
            }


            }


//            TedImagePicker.with(context!!).max(12,"maximum")
//                .min(2,"minimum").selectedUri(imagesPathListUri)
//                .mediaType(gun0912.tedimagepicker.builder.type.MediaType.IMAGE)
//                .startMultiImage { uriList ->showMulti(uriList)
//
//                  }

//            TedRxImagePicker.with(context!!)
//                .startMultiImage()
//                .subscribe({ uriList ->
//                    showMulti(uriList)
//                }, Throwable::printStackTrace)


            }



        }
//
//        ImagePicker.create(this)
//            .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
//            .folderMode(true) // folder mode (false by default)
//            .toolbarFolderTitle("Folder") // folder selection title
//            .toolbarImageTitle("Tap to select") // image selection title
//            .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
//
//
//            .multi() // multi mode (default mode)
//            .limit(12) // max images can be selected (99 by default)

//
//
//
//
//
//            .enableLog(false) // disabling log
//
//            .start(); // start image picker activity with request code



//   override fun onActivityResult(requestCode:Int,resultCode:Int, data:Intent) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//            // Get a list of picked images
//            List<Image> images = ImagePicker.getImages(data)
//            // or get a single image only
//            Image image = ImagePicker.getFirstImageOrNull(data)
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


    fun showMulti(uriList:List<Uri>){

        addedPictureList?.adapter = context?.let {
            PictureAdapter(
                it, this,
                uriList.toTypedArray()
            )
        }


                    var count = uriList.size
        context?.toast(count.toString())
                    for (i in 0 until count!!) {
                        var imageUri: Uri = uriList.get(i)
                        getPathFromURI(imageUri)

                        var oregnal = File(imagesPathList.get(i))
//                        var oregnal = File(getPathFromURI(imageUri))
                        imageFile = RequestBody.create(
                            MediaType.parse(context?.contentResolver?.getType(imageUri)!!),
                            oregnal
                        )
                        if(i==0){
                            imagesBodyList.add(i,MultipartBody.Part.createFormData("image", oregnal.name, imageFile))

                        }else{
                            imagesBodyList.add(i,MultipartBody.Part.createFormData("image${i-1}", oregnal.name, imageFile))

                        }

                    }
    }

    fun Reuseable(
        array: ArrayList<String>,
         colorAdd: AppCompatButton
    ):Int{
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterTitle.text="Add"
        dview.filterPicker.minValue = 0
        dview.filterPicker.maxValue = array.size-1
        dview.filterPicker.wrapSelectorWheel = true
        dview.filterPicker.displayedValues = array.toTypedArray()
        var index=0
        var index2=0
        dview.filterPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            //
            //Display the newly selected number to text view
            index=newVal
            // println(country +"   cooodkl,dl")
        }
        dview.applayFilter.setOnClickListener {

            //mileFilter.text=selectMile

            index2=index
            colorAdd.text=array[index2]
            when(colorAdd.id){
                R.id.ColorAdd->color=array[index2]
                R.id.statusAdd->status=array[index2]
                R.id.YearAdd->year=array[index2].trim().toInt()
                R.id.sourceAdd->warid=array[index2]
                R.id.MilesAdd->mileage=array[index2].trim() .toInt()
                R.id.cylenderAdd->cylinders=array[index2].trim().toInt()
                R.id.GearAdd->gear=array[index2]
                R.id.TransmissionTypeAdd->driveSystem=array[index2]
                R.id.FuelTypeAdd->fuel=array[index2]
                R.id.RoofAdd->roof=array[index2]
                R.id.DouchmhAdd->type=array[index2]

            }
            colorAdd.text=array[index2]
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }
     return index2
    }
    override fun OnStart() {
        BtnAdd.isClickable=false
//        navBar?.visibility=View.GONE
//        //keyFlag=false
//
//        toolbar?.visibility=View.GONE

       // navBar?.setOnClickListener(null);

        animation_loadingAdd?.visibility=View.VISIBLE
        animation_loadingAdd?.playAnimation()
    }

    override fun onSucsess(CarResponse: addRes) {
        if(imagesBodyList.size>1){
            for(i in 1 until imagesBodyList.size){
                imagesBodyList2.add(i-1,imagesBodyList.get(i))
            }
            context?.toast(resources.getString(R.string.PleaseWait))
            viewModel.addImages(imagesBodyList2,CarResponse.data.id)
        }else{
            if(!keyFlag){
            animation_loadingAdd?.visibility=View.INVISIBLE

                val dview: View? = layoutInflater?.inflate(R.layout.info, null)
                val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
                val malert= builder?.show()

                malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dview?.info?.text=resources.getString(R.string.showYourAdd)

                dview?.goLog?.text=resources.getString(R.string.Profile)
                dview?.goLog?.setOnClickListener {
                    malert?.dismiss()
                    view?.findNavController()?.navigate(R.id.myAdd)
                }}


//            navBar?.visibility=View.VISIBLE
//
//
//            toolbar?.visibility=View.VISIBLE

            BtnAdd?.isClickable=true
        }



    }

    override fun onFailer(message: String) {
        context?.toast(message)
        animation_loadingAdd?.visibility=View.INVISIBLE
        BtnAdd.isClickable=true
        println(message)

        navBar?.visibility=View.VISIBLE


        toolbar?.visibility=View.VISIBLE


    }


    override fun OnStartAddImages() {
        context?.toast("start images")
    }

    override fun onSucsessAddImages(message: String) {
        //context?.toast("Added sucessfully")


        animation_loadingAdd?.visibility=View.INVISIBLE
           if(!keyFlag){
               val dview: View? = layoutInflater?.inflate(R.layout.info, null)
               val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
               val malert= builder?.show()

               malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

               dview?.info?.text=resources.getString(R.string.showYourAdd)

               dview?.goLog?.text=resources.getString(R.string.Profile)
               dview?.goLog?.setOnClickListener {
                   malert?.dismiss()
                   view?.findNavController()?.navigate(R.id.myAdd)
               }
           }




        navBar?.visibility=View.VISIBLE


        toolbar?.visibility=View.VISIBLE

        BtnAdd?.isClickable=true



    }

    override fun OnFailerAddImages(message: String) {
        context?.toast(message)
        BtnAdd?.isClickable=true
        animation_loadingAdd?.visibility=View.INVISIBLE
        navBar?.visibility=View.VISIBLE


        toolbar?.visibility=View.VISIBLE
    }
    override fun onNoteClick(position: Int) {
        Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
        imagesPathListUri.removeAt(position)

            imagesBodyList.removeAt(position)

        addedPictureList?.adapter = context?.let {
            PictureAdapter(
                it, this,
                imagesPathListUri.toTypedArray()
            )
        }
    }
    fun showClass(
        array: ArrayList<String>,
        carClassAdd: AppCompatButton
    ){
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterTitle.text="Add"
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
            indClass=index-1
            carClassAdd.text=array[index]
            `class`=array[index]
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }

    }

    fun showBrand(
        array: ArrayList<String>,
        carClassAdd: AppCompatButton
    ){
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterTitle.text="Add"
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
            indBrand=index-1
            carModelAdd.text=array[index]
            brand=array[index]
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }

    }
    fun showLoc2(
        array: ArrayList<String>,
        carClassAdd: AppCompatButton
    ){
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterTitle.text="Add"
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
            //indBrand=index-1
            carModelAdd.text=array[index]
            state=array[index]

            provincAdd.text=location+"/"+array[index]
            malert?.dismiss()
        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }

    }
    fun showLoc(
        array: ArrayList<String>,
        carClassAdd: AppCompatButton
    ){
        val dview: View = layoutInflater.inflate(R.layout.filters_dilog1, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()
        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.filterTitle.text="Add"
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
            indLoc=index-1
            carModelAdd.text=array[index]
            location=array[index]
            if(indLoc!=-1){
                malert?.dismiss()
                showLoc2(this!!.loc2?.get(indLoc)?.data as ArrayList<String>, CarClassAdd)
            }


        }
        dview.closeDf.setOnClickListener {
            malert?.dismiss()
            index=0
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
            && null != data
        ) {
            if (data.getClipData() != null) {

                    for (i in 0 until data.getClipData()!!.itemCount) {
                        val uri = data.getClipData()!!.getItemAt(i).uri
                        imagesPathListUri.add(i, uri)

                        addedPictureList?.adapter = context?.let {
                            PictureAdapter(
                                it, this,
                                imagesPathListUri.toTypedArray()
                            )
                        }

                    }

//                    var imageUri: Uri = data.clipData.getItemAt(0).uri
//                    getPathFromURI(imageUri)
//                    var count = data.clipData.itemCount
//                    var oregnal = File(imagesPathList.get(0))
//                    imageFile = RequestBody.create(
//                        MediaType.parse(context?.contentResolver?.getType(imageUri)),
//                        oregnal
//                    )
//
//                    image = MultipartBody.Part.createFormData("image", oregnal.name, imageFile)
                    //imagesPathList.clear()
                    var count = data.clipData?.itemCount
                    for (i in 0 until count!!) {
                        var imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        getPathFromURI(imageUri)

                        var oregnal = File(imagesPathList.get(i))
//                        var oregnal = File(getPathFromURI(imageUri))
                        imageFile = RequestBody.create(
                            MediaType.parse(context?.contentResolver?.getType(imageUri)!!),
                            oregnal
                        )
                        if(i==0){
                            imagesBodyList.add(i,MultipartBody.Part.createFormData("image", oregnal.name, imageFile))

                        }else{
                            imagesBodyList.add(i,MultipartBody.Part.createFormData("image${i-1}", oregnal.name, imageFile))

                        }

                    }


            }else{
                context?.toast(resources.getString(R.string.multiple))
            }
                //single image
//            } else if (data.getData() != null) {
//                val uri: Uri? = data?.data
//                    uri?.let { imagesPathListUri.add(0, it) }
//                    addedPictureList?.adapter = context?.let {
//                        PictureAdapter(
//                            it, this,
//                            imagesPathListUri.toTypedArray()
//                        )}
//
//
//                var imagePath: String = data.data.path
//                var oregnal=File(imagePath)
//                    imageFile= RequestBody.create(
//                        MediaType.parse(context?.contentResolver?.getType(uri)),
//                        oregnal)
//
//                    image=MultipartBody.Part.createFormData("image", oregnal.name,imageFile)
//                Log.e("imagePath", imagePath);
//            }


        }
    }




//    override  fun onActivityResult(requestCode:Int , resultCode:Int, data:Intent) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        easyImage.handleActivityResult(requestCode, resultCode, data, this, DefaultCallback {
//            override fun onMediaFilesPicked(imageFiles:List<MediaFile>,  source: MediaSource) {
//             //   onPhotosReturned(imageFiles);
//            }
//
//            override fun onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
//                //Some error handling
//                error.printStackTrace();
//            }
//
//            override fun onCanceled(@NonNull MediaSource source) {
//                //Not necessary to remove any files manually anymore
//            }
//        });
//    }


    @SuppressLint("NewApi")
    fun getPathFromURI(uri: Uri) {
        var path: String = uri.path!! // uri = any content Uri

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = context?.contentResolver ?.query(
                databaseUri,
                projection, selection, selectionArgs, null
            )
            if(cursor==null){
                imagePath = path
            }else
            if (cursor!!.moveToFirst()) {
                val columnIndex = cursor!!.getColumnIndex(projection[0])
              //  if (cursor.getType(columnIndex) == FIELD_TYPE_STRING) {
                    imagePath = cursor!!.getString(columnIndex)
               // }

                // Log.e("path", imagePath);

            }

            imagesPathList.add(imagePath)
            cursor?.close()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

//
//    fun getPathFromURI2(uri: Uri) {
//        var path: String = File(uri.path!!).absolutePath // uri = any content Uri
//
//
//        val cursor = context?.contentResolver ?.query(
//            uri,
//            null, null, null, null
//        )
//        if(cursor==null){
//            imagePath = uri.path!!
//        }else
//            { cursor.moveToFirst()
////                val columnIndex = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.)
//                imagePath = cursor.getString(columnIndex);
//                cursor.close();
//
//
//                // Log.e("path", imagePath);
//            }
//        imagesPathList.add(imagePath)
//    }

    fun dumpImageMetaData(uri: Uri) {

        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        val cursor: Cursor? = context!!.contentResolver.query( uri, null, null, null, null, null)

        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i(TAG, "Display Name: $displayName")

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                val size: String = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)

                } else {
                    "Unknown"
                }
                imagesPathList.add(size)
                Log.i(TAG, "Size: $size")
            }
        }
    }
 fun checkPermissionREAD_EXTERNAL_STORAGE(
             context: Context
 ):Boolean {
        var currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                showDialog("External storage", context,
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity ,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                context as Activity ,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

fun showDialog(  msg:String, context:Context ,
              permission:String) {
        var alertBuilder:AlertDialog.Builder = AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                 DialogInterface.OnClickListener(
                     fun(dialog: DialogInterface, which:Int) {
                         ActivityCompat.requestPermissions(context as Activity,
                             arrayOf(permission ),
                             MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                     }
                 )



                )
       val  alert:AlertDialog = alertBuilder.create();
        alert.show();
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action= Intent.ACTION_GET_CONTENT
                    // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    context?.toast("tou can not pick image")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        context!!.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }
    override fun onDetach() {
        keyFlag=true
        navBar?.visibility=View.VISIBLE
        toolbar?.visibility=View.VISIBLE
        super.onDetach()
    }


    fun getUriRealPath(ctx:Context ,  uri:Uri):String
    {
        var ret = "";

        if( isAboveKitKat() )
        {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri);
        }else
        {
            // Android OS below sdk version 19
            ret = getImageRealPath(context!!.contentResolver, uri, null);
        }

        return ret;
    }
    @SuppressLint("NewApi")
    fun getUriRealPathAboveKitkat(ctx:Context ,  uri:Uri):String
    {
        var ret = "";

        if(ctx != null && uri != null) {

            if(isContentUri(uri))
            {
                if(isGooglePhotoDoc(uri.getAuthority()!!))
                {
                    ret = uri.getLastPathSegment()!!
                }else {
                    ret = getImageRealPath(ctx.contentResolver, uri, null);
                }
            }else if(isFileUri(uri)) {
                ret = uri.path!!;
            }else if(isDocumentUri(ctx, uri)){

                // Get uri related document id.
                var documentId = DocumentsContract.getDocumentId(uri);

                // Get uri authority.
                var uriAuthority = uri.getAuthority();

                if(isMediaDoc(uriAuthority!!))
                {
                    var idArr = documentId.split(":");
                    if(idArr.size == 2)
                    {
                        // First item is document type.
                        var docType = idArr[0];

                        // Second item is document real id.
                        var realDocId = idArr[1];

                        // Get content uri by document type.
                        var mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if("image".equals(docType))
                        {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }

                        // Get where clause with real document id.
                        var whereClause = MediaStore.Images.Media._ID + " = " + realDocId;

                        ret = getImageRealPath(ctx.contentResolver , mediaContentUri, whereClause);
                    }

                }else if(isDownloadDoc(uriAuthority!!))
                {
                    // Build download uri.
                    var downloadUri = Uri.parse("content://downloads/public_downloads");

                    // Append download document id at uri end.
                    var downloadUriAppendId = ContentUris.withAppendedId(downloadUri,documentId.toLong());

                    ret = getImageRealPath(ctx.contentResolver, downloadUriAppendId, null);

                }
            }
        }

        return ret;
    }

/* Check whether current android os version is bigger than kitkat or not. */
    fun isAboveKitKat():Boolean
    {
        var ret = false;
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        return ret;
    }

/* Check whether this uri represent a document or not. */
@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.KITKAT)
fun  isDocumentUri(ctx:Context, uri:Uri):Boolean
    {
        var ret = false;
        if(ctx != null && uri != null) {
            ret = DocumentsContract.isDocumentUri(ctx, uri);
        }
        return ret;
    }

/* Check whether this uri is a content uri or not.
*  content uri like content://media/external/images/media/1302716
*  */
    fun  isContentUri( uri:Uri):Boolean
    {
        var ret = false;
        if(uri != null) {
            var uriSchema = uri.getScheme();
            if("content".equals(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }

/* Check whether this uri is a file uri or not.
*  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
* */
    fun  isFileUri( uri:Uri):Boolean
    {
        var ret = false;
        if(uri != null) {
            var uriSchema = uri.getScheme();
            if("file".equals(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }


/* Check whether this document is provided by ExternalStorageProvider. */
    fun  isExternalStoreDoc( uriAuthority:String):Boolean
    {
        var ret = false;

        if("com.android.externalstorage.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

/* Check whether this document is provided by DownloadsProvider. */
   fun isDownloadDoc( uriAuthority:String):Boolean
    {
        var ret = false;

        if("com.android.providers.downloads.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

/* Check whether this document is provided by MediaProvider. */
    fun  isMediaDoc( uriAuthority:String):Boolean
    {
        var ret = false;

        if("com.android.providers.media.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

/* Check whether this document is provided by google photos. */
    fun  isGooglePhotoDoc( uriAuthority:String):Boolean
    {
        var ret = false;

        if("com.google.android.apps.photos.content".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

/* Return uri represented document file real local path.*/
    fun getImageRealPath(contentResolver: ContentResolver, uri:Uri, whereClause:String?):String
    {
        var ret = ""

        // Query the uri with condition.
        var cursor = contentResolver.query(uri, null, whereClause, null, null);

        if(cursor!=null)
        {
            var moveToFirst = cursor.moveToFirst();
            if(moveToFirst)
            {

                // Get columns name by uri type.
                var columnName = MediaStore.Images.Media.DATA;

                if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Images.Media.DATA;
                }else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Audio.Media.DATA;
                }else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Video.Media.DATA;
                }

                // Get column index.
                var imageColumnIndex = cursor.getColumnIndex(columnName);

                // Get column value which is the uri related file local path.
                if(imageColumnIndex!=-1)
                ret = cursor.getString(imageColumnIndex);
            }
        }

        return ret;
    }
}
