package com.mustafayusef.sharay.ui.profile.myads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.UserInfo
import kotlinx.android.synthetic.main.fragment_my_add.*


class myAdd : Fragment(),myCarsAdapter.OnNoteLisener {

    private lateinit var navController: NavController
    var use:UserInfo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)



        val type:UserInfo? = arguments?.getSerializable("Cars") as UserInfo
        use=type
        myCarsList?.layoutManager= LinearLayoutManager(context)
        myCarsList?.adapter= context?.let { type?.let { it1 -> myCarsAdapter(it ,this, it1.Cars) } }
 }

    override fun onNoteClick(position: Int) {
        val carId= use?.Cars?.get (position)!!.id
        var bundle = bundleOf("carId" to carId)
        view?.findNavController()?.navigate(R.id.carDetails,bundle)
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
    }




