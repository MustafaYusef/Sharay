package com.mustafayusef.sharay.ui.auth.signin

import android.view.View
import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.auth.AuthLesener

class SignUpUserViewModel(val repostary: userRepostary) : ViewModel() {

    var email:String?=null
    var password:String?=null
    var phone:String?=null
    var name:String?=null
    var Auth: AuthLesener?=null

    fun SignIn(view: View){
        Auth?.OnStart()
        if(email.isNullOrEmpty()||password.isNullOrEmpty()||phone.isNullOrEmpty()||name.isNullOrEmpty()){
            Auth?.onFailer("invalid password or email")
            return
        }
        corurtins.main {
            try {
                val onewayResponse=repostary.getSignUpDate(email!!,password!!,phone!!,name!!)
                onewayResponse ?.let {
                    Auth?.onSucsessSignIn(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }}

        }

    }
}
