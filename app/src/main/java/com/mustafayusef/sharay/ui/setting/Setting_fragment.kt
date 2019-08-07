package com.mustafayusef.sharay.ui.setting

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.LinearLayout

import java.util.*
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mustafayusef.sharay.R
import kotlinx.android.synthetic.main.languge_setting.view.*

import kotlinx.android.synthetic.main.setting_fragment_fragment.*

import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.MainActivity.cacheObj.language


class Setting_fragment : Fragment() {
    private lateinit var navController: NavController
    companion object {
        fun newInstance() = Setting_fragment()
    }

    private lateinit var viewModel: SettingFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        return inflater.inflate(com.mustafayusef.sharay.R.layout.setting_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        languagClick.setOnClickListener {
            showSearch()
        }


//        val config = resources.configuration
//        val locale = Locale("en")
//        Locale.setDefault(locale)
//        config.locale = locale
//        resources.updateConfiguration(config, resources.displayMetrics)


//        btn_english.setOnClickListener(object : OnClickListener() {
//
//            fun onClick(v: View) {
//                val locale = Locale("en")
//                Locale.setDefault(locale)
//                val config = Configuration()
//                config.locale = locale
//                getBaseContext().getResources()
//                    .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics())
//                Toast.makeText(this, resources.getString(R.string.lbl_langSelectEnglis), Toast.LENGTH_SHORT).show()
//
//            }
//        })
//
//
//
//        btn_arbice.setOnClickListener(object : OnClickListener() {
//
//            fun onClick(v: View) {
//                val locale = Locale("ar")
//                Locale.setDefault(locale)
//                val config = Configuration()
//                config.locale = locale
//                getBaseContext().getResources()
//                    .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics())
//                Toast.makeText(this, resources.getString(R.string.lbl_langSelecURdu), Toast.LENGTH_SHORT).show()
//
//            }
//        })
    }
    fun showSearch(){
        val view = layoutInflater.inflate(com.mustafayusef.sharay.R.layout.languge_setting , null)

        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = size.x
        val height = size.y


        //view.minimumHeight=600
        val mBottomSheetDialog = Dialog(
            context,
            R.style.MaterialDialogSheet
        )
        mBottomSheetDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(true)
        mBottomSheetDialog.window.setLayout(
            width-100,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mBottomSheetDialog.window.setGravity(Gravity.BOTTOM)

        mBottomSheetDialog.show()
        view.closeLang .setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        view.changeArbic.setOnClickListener {
            val locale = "ar"

            //Change Application level locale
            language=locale
            if(MainActivity.cacheObj.language!=""){
                activity?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

            }
            activity?.recreate()
            mBottomSheetDialog.dismiss()
        }

        view.changeEnglish.setOnClickListener {
            val locale="en"
            language=locale

            if(MainActivity.cacheObj.language!=""){
                activity?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

            }
            activity?.recreate()
            mBottomSheetDialog.dismiss()
        }



    }
}
