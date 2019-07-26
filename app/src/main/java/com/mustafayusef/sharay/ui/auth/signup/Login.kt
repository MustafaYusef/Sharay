package com.mustafayusef.sharay.ui.auth.signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.databinding.LoginFragmentBinding
import com.mustafayusef.sharay.ui.MainActivity

import com.mustafayusef.sharay.ui.auth.AuthLesener

import kotlinx.android.synthetic.main.login_fragment.*


class Login : Fragment(), AuthLesener {
    override fun onSucsessSignIn(loginResponse: signInModel) {

    }


    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        var binding  = DataBindingUtil.inflate(inflater ,
//            R.layout.login_fragment,container , false)
//        var myView : View  = binding.root
//        //here data must be an instance of the class MarsDataProvider
//        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
//        binding.viewm(viewModel)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= userRepostary(api!!)
        val factory= SignUpFactory(repostary)
        var binding:LoginFragmentBinding  =DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
       binding.viewmodel=viewModel

        viewModel?.Auth=this
        binding?.executePendingBindings()



        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar> (R.id.ToolBar)
        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.signUp) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE
            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }

        }

        context?.let { Glide.with(it).load(R.drawable.sharay_logo).into(logInPhoto) }

        goRegester.setOnClickListener {
            view?.findNavController()?.navigate(R.id.goToRegester)

        }
        // TODO: Use the ViewModel


    }

    override fun OnStart() {
        animation_loadingSignUp.visibility=View.VISIBLE
        animation_loadingSignUp.playAnimation()
    }

    override fun onSucsess(loginResponse: signUp) {
        animation_loadingSignUp.visibility=View.GONE
        animation_loadingSignUp.pauseAnimation()
        MainActivity.cacheObj.token=loginResponse.token
        view?.findNavController()?.navigate(R.id.add_fragment)

    }

    override fun onFailer(message: String) {
        animation_loadingSignUp.visibility=View.GONE
        animation_loadingSignUp.pauseAnimation()
        context?.toast(message)
    }
}


