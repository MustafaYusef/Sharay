package com.mustafayusef.sharay.ui.sections

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.sections_fragment_fragment.*
import kotlinx.android.synthetic.main.sections_fragment_fragment.view.*

class Sections_fragment : Fragment() {
    private lateinit var navController: NavController
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
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }

        CarCompany.setOnClickListener {
           val type= "stores"
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }

        CarForRent.setOnClickListener {
            val type= "rent"
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }

        CarNumbers.setOnClickListener {
            val type= "numbers"

            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)

//            val action =Sections_fragmentDirections.SectionsToPublic("numbers")
//            view?.findNavController()?.navigate(action)
        }

        PartsCar.setOnClickListener {
            val type= "parts"
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }

        waredCar.setOnClickListener {
            val type= "import"
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }

        Motore.setOnClickListener {
            val type= "motore"
            var bundle = bundleOf("type" to type)
            view.findNavController().navigate(R.id.SectionsToPublic, bundle)
        }
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.careRent) {
                toolbar?.visibility = View.GONE
                //toolbar?.visibility = View.GONE

            } else {
                toolbar?.visibility = View.VISIBLE
               // toolbar?.visibility = View.VISIBLE
            }
        }



    }

}
