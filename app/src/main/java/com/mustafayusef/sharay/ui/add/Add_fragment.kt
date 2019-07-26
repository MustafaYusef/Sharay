package com.mustafayusef.sharay.ui.add

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.add_fragment_fragment.*
import kotlinx.android.synthetic.main.car_details_fragment.*

import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.sharay.ui.main.MainAdapter

import kotlinx.android.synthetic.main.added_image_card.*
import kotlinx.android.synthetic.main.added_image_card.view.*
import kotlinx.android.synthetic.main.main_fragment_fragment.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Add_fragment : Fragment(),PictureAdapter.OnNoteLisener {


    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<Uri> = arrayListOf()

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

        viewModel = ViewModelProviders.of(this).get(AddFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.add_fragment) {

                if(MainActivity.cacheObj.token=="")
                    view?.findNavController()?.navigate(R.id.fromAddToLogin)

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addedPictureList?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,
            listOf(resources.getString(R.string.sections), resources.getString(R.string.Carforsale)
                ,resources.getString(R.string.carparts),resources.getString(R.string.motorcycle),
            resources.getString(R.string.carrent),resources.getString(R.string.carcompany),
                resources.getString(R.string.carnumber),resources.getString(R.string.importcar)))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        showList.setOnClickListener {
            view?.findNavController()?.navigate(R.id.goToSectionList)
//            val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
//            val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)
//
//            view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
//                if(destination.id == R.id.carDetails) {
//                    navBar?.visibility = View.GONE
//                    toolbar?.visibility = View.GONE
//                } else {
//                    navBar?.visibility = View.VISIBLE
//                    toolbar?.visibility = View.VISIBLE
//                }
//
//            }
        }

        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)




        openGallary.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

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
                }
            }
        }

    }
}
