package com.mustafayusef.sharay.ui.profile.myads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter

import com.mustafayusef.sharay.R

import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.profile.myFavarote.MyFavFactory
import com.mustafayusef.sharay.ui.profile.myFavarote.MyFavLesener
import com.mustafayusef.sharay.ui.profile.myFavarote.MyFavViewModel
import kotlinx.android.synthetic.main.fragment_my_fav.*


class myFav : Fragment(),myFavAdapter.OnNoteLisener,MyFavLesener {

 var res:List<favoriteModel>?=null
    private lateinit var navController: NavController
    private lateinit var viewModel: MyFavViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= CarsRepostary(api!!)
        val factory= MyFavFactory(repostary)
        viewModel= ViewModelProviders.of(this,factory).get(MyFavViewModel::class.java)


        viewModel?.Fav=this
        viewModel.GetFavorite(MainActivity.cacheObj.token)

 }

    override fun onNoteClick(position: Int) {
        val carId= res?.get(position)!!.carId
        val action = myFavDirections .actionMyFavToCarDetails(carId)
        view?.findNavController()?.navigate(action)
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

    override fun OnStartFav() {
    }

    override fun onSucsessFav(CarResponse: List<favoriteModel>) {
        res=CarResponse
        myFavList?.layoutManager= LinearLayoutManager(context)
        myFavList?.adapter= context?.let  {  myFavAdapter(it ,this, CarResponse) }
    }

    override fun onFailerFav(message: String) {

    }
    }




