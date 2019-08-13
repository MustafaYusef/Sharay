package com.mustafayusef.sharay.ui.auth.signin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.bulk
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.databinding.SignUpUserFragmentBinding
import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.auth.AuthLesener
import com.mustafayusef.sharay.ui.auth.signup.Login
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.sign_up_user_fragment.*
import kotlinx.android.synthetic.main.sign_up_user_fragment.signPhoto

class SignUpUser : Fragment(),AuthLesener {

    private lateinit var navController: NavController
    companion object {
        fun newInstance() = SignUpUser()
    }

    private lateinit var viewModel: SignUpUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= userRepostary(api!!)
        val factory= SignInUserFactory(repostary)
        var binding:SignUpUserFragmentBinding  =
            DataBindingUtil.inflate(inflater,R.layout.sign_up_user_fragment,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(SignUpUserViewModel::class.java)
        binding.viewmodel=viewModel

        viewModel?.Auth=this
        binding?.executePendingBindings()



        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignUpUserViewModel::class.java)

        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.signUpUser) {
                navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE
            } else {
                navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
        context?.let { Glide.with(it).load(com.mustafayusef.sharay.R.drawable.sharay_logo).into(signPhoto) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        passText?.transformationMethod=PasswordTransformationMethod()
    }
    override fun OnStart() {
        animation_loadingSignIn.visibility=View.VISIBLE
        animation_loadingSignIn.playAnimation()
    }

    override fun onSucsess(loginResponse: signUp) {
    }

    override fun onSucsessSignIn(loginResponse: signInModel) {
        animation_loadingSignIn.visibility=View.GONE
        animation_loadingSignIn.pauseAnimation()
        if( !loginResponse.token.isNullOrEmpty()){

            view?.findNavController()?.navigate(R.id.signUp)
            context?.toast("you have account now")
        }else{
            context?.toast("you already have account")

        }



    }

    override fun onFailer(message: String) {
        animation_loadingSignIn.visibility=View.GONE
        animation_loadingSignIn.pauseAnimation()
        context?.toast(message)
    }
}
