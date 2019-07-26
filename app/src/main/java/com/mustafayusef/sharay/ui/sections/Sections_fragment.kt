package com.mustafayusef.sharay.ui.sections

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.sections_fragment_fragment.*
import kotlinx.android.synthetic.main.sections_fragment_fragment.view.*

class Sections_fragment : Fragment() {

    companion object {
        fun newInstance() = Sections_fragment()
    }

    private lateinit var viewModel: SectionsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.sections_fragment_fragment, container, false)
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        view.carForSale .setOnClickListener {
            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show()
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SectionsFragmentViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carForSale.setOnClickListener {
            val type= "cars"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        CarCompany.setOnClickListener {
           val type= "stores"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        CarForRent.setOnClickListener {
            val type= "rent"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        CarNumbers.setOnClickListener {
            val type= "numbers"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        PartsCar.setOnClickListener {
            val type= "parts"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        waredCar.setOnClickListener {
            val type= "wared"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }

        Motore.setOnClickListener {
            val type= "motore"
            val action =Sections_fragmentDirections.SectionsToPublic(type)
            view?.findNavController()?.navigate(action)
        }




    }

}
