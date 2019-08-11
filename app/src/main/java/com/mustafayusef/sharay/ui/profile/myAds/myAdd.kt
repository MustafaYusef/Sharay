package com.mustafayusef.sharay.ui.profile.myAds

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.signUp

import com.mustafayusef.sharay.data.models.userModels.UserCars
import com.mustafayusef.sharay.data.models.userModels.UserInfo
import com.mustafayusef.sharay.data.networks.delete
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.auth.signup.Login
import com.mustafayusef.sharay.ui.profile.ProfileFragmentViewModel
import com.mustafayusef.sharay.ui.profile.profileFactory
import com.mustafayusef.sharay.ui.profile.profileLesener
import kotlinx.android.synthetic.main.desc_parts.view.*
import kotlinx.android.synthetic.main.fragment_my_add.*


class myAdd : Fragment(),myCarsAdapter.OnNoteLisener ,profileLesener{
    override fun onSucsessLog(loginResponse: signUp) {

    }

    override fun OnStart() {
        noNetContainerMyadd?.visibility=View.GONE
        animation_loadingMyadd?.visibility=View.VISIBLE
    }

    override fun onSucsess(CarResponse: UserInfo) {
        animation_loadingMyadd?.visibility=View.GONE
        animation_loadingMyadd?.pauseAnimation()
        use=CarResponse.Cars
        myCarsList?.adapter= context?.let { CarResponse.Cars?.let { it1 -> myCarsAdapter(it ,this, it1,viewModel) } }

    }

    override fun onFailer(message: String) {
        noNetContainerMyadd?.visibility=View.VISIBLE
        animation_loadingMyadd?.visibility=View.GONE
        animation_loadingMyadd?.pauseAnimation()
    }

    private lateinit var viewModel: ProfileFragmentViewModel
    var use:List<UserCars>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

        val networkIntercepter= context?.let { com.mustafayusef.holidaymaster.networks.networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= userRepostary(api!!)
        val factory= profileFactory(repostary)
        viewModel = ViewModelProviders.of(activity!!,factory).get(
            ProfileFragmentViewModel::class.java)
     viewModel.Auth=this
        retryBtnMyAdd?.setOnClickListener {
            viewModel.profile(MainActivity.cacheObj .token)
        }
       // context?.toast("my add fragment")
//        val type:UserInfo = arguments?.getSerializable("Cars") as UserInfo
//       use=type.Cars
        viewModel.profile(MainActivity.cacheObj .token)
        myCarsList?.layoutManager= LinearLayoutManager(context)
 }

    override fun onNoteClick(position: Int) {
        if(use?.get (position)!!.active){
            val carId= use?.get (position)!!.id
            var bundle = bundleOf("carId" to carId)
            view?.findNavController()?.navigate(R.id.carDetails,bundle)
            val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
            val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

            view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.carDetails) {
                    navBar?.visibility = View.GONE
                    toolbar?.visibility = View.GONE

                } else {
                    navBar?.visibility = View.VISIBLE
                    toolbar?.visibility = View.VISIBLE
                }
            }
        }else{
            val dview: View = layoutInflater.inflate(R.layout.desc_parts, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            dview.title.text="عذراً"
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview.descPart?.text="لم تتم الموافقه على اعلانك بعد"
        }

    }
    override fun OnStartDeletCar() {
      context?.toast("start delet")
    }

    override fun onSucsessDeletCar(CarResponse: delete) {

        context?.toast(CarResponse.data)
        viewModel.profile(MainActivity.cacheObj .token)
    }

    override fun onFailerDeletCar(message: String) {
        context?.toast(message)
    }
    }





