package com.mustafayusef.sharay.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.UserCars
import com.mustafayusef.sharay.data.models.UserInfo
import com.mustafayusef.sharay.data.networks.myApi

import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.profile_fragment_fragment.*


class Profile_fragment : Fragment(),profileLesener {
    private lateinit var navController: NavController
    companion object {
        fun newInstance() = Profile_fragment()
    }

    private lateinit var viewModel: ProfileFragmentViewModel
    var myCar:UserInfo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        return inflater.inflate(R.layout.profile_fragment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= userRepostary(api!!)
        val factory=profileFactory(repostary)
        viewModel=ViewModelProviders.of(activity!!,factory).get(ProfileFragmentViewModel::class.java)
        viewModel.Auth =this
        if(MainActivity.cacheObj.token!=""){
            viewModel.profile(MainActivity.cacheObj.token)
        }else{
            view?.findNavController()!!.navigate(R.id.goToLogin)
        }


        myAdBtn.setOnClickListener {

            var bundle = bundleOf("Cars" to myCar)
            view?.findNavController()?.navigate(R.id.goToMyCars, bundle)
        }
        favBtn.setOnClickListener {


            view?.findNavController()?.navigate(R.id.action_profile_fragment_to_myFav)
        }
        logOut.setOnClickListener {
            MainActivity.cacheObj.token=""
            view?.findNavController()?.navigate(R.id.goToLogin)
        }
    }
   override fun OnStart(){

       animation_loadingProfile?.visibility=View.VISIBLE
       animation_loadingProfile?.playAnimation()
   }
    override fun onSucsess(userRes: UserInfo){
        animation_loadingProfile?.visibility=View.GONE
        profileContainer?.visibility=View.VISIBLE
        MainActivity.cacheObj.name=userRes.name
        MainActivity.cacheObj.phone=userRes.phone

       MainActivity.cacheObj.Email=userRes.email
        MainActivity.cacheObj.id=userRes.id
        nameUser?.text=MainActivity.cacheObj.name
        phoneUser?.text=MainActivity.cacheObj.phone
        secondPhone?.text=MainActivity.cacheObj.Secondphone
        emailUser?.text=MainActivity.cacheObj.Email

        myCar=userRes
    }


    override fun onFailer(message:String){
        context?.toast(message)
        animation_loadingProfile?.visibility=View.GONE

    }
}
