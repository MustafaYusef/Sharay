package com.mustafayusef.sharay.ui.auth.signup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation


import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.chibatching.kotpref.Kotpref
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
import com.mustafayusef.sharay.databinding.LoginFragmentBinding
import com.mustafayusef.sharay.ui.MainActivity

import com.mustafayusef.sharay.ui.auth.AuthLesener

import kotlinx.android.synthetic.main.desc_parts.view.*
import kotlinx.android.synthetic.main.info.view.*

import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.setting_fragment_fragment.*
import kotlinx.android.synthetic.main.sign_up_user_fragment.*


class Login : Fragment(), AuthLesener {

    override fun onSucsessSignIn(loginResponse: signInModel) {

    }

    private lateinit var navController: NavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Kotpref.init(context!!)
        Password?.transformationMethod= PasswordTransformationMethod()
           val dview: View = layoutInflater.inflate(R.layout.info, null)
        val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
        val malert= builder?.show()

        malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dview.titleInfo.text=getResources().getString(R.string.logInStep)
        dview.info?.text=  "تسجيل الدخول\n" +
                    "خطوات بسيطة للتسجيل و اعلان سيارتك في السوق المركزي للسيارات\n" +
                    "\n" +
                    "انقر على ( تسجيل الدخول ) في الصفحة الرئيسية ثم كلمة (التسجيل)\n" +
                    "ادخل بياناتك (الاسم الكامل) اضافة الى رقم الهاتف الخاص بكم.\n" +
                    "سوف تتلقى رسالة نصية على رقم هاتفكم فيها رمز ، يتم ادخاله في الحقل المطلوب لتفعيل اشتراكك\n" +
                    "ايضاً يمكنك ادخال بريدكم الالكتروني .\n"




        dview.goLog?.setOnClickListener {
            malert?.dismiss()
        }
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

        MainActivity.cacheObj .token =loginResponse.token
        println("looooooooooooooooooooooooog in   " +MainActivity.cacheObj .token)
//        MainActivity.cacheObj .phoneLogin=viewModel.phone!!
//        MainActivity.cacheObj .PasswordLogin=viewModel.password!!
//            context?.toast(MainActivity.cacheObj .PasswordLogin)
//        context?.toast(MainActivity.cacheObj .phoneLogin)
        view?.findNavController()?.navigate(R.id.profile_fragment)

    }

    override fun onFailer(message: String) {
        animation_loadingSignUp.visibility=View.GONE
        animation_loadingSignUp.pauseAnimation()
        context?.toast(message)
    }
}


