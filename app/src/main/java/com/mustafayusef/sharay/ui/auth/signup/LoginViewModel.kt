package com.mustafayusef.sharay.ui.auth.signup

import android.view.View
import androidx.lifecycle.ViewModel
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.auth.AuthLesener

class LoginViewModel(val repostary: userRepostary) : ViewModel() {

    var userName:String?=null
    var password:String?=null
       var Auth: AuthLesener?=null

    fun Login(view: View){
        Auth?.OnStart()
        if(userName.isNullOrEmpty()||password.isNullOrEmpty()){
            Auth?.onFailer("invalid password or email")
            return
        }
                corurtins.main {
                    try {
                        val onewayResponse=repostary.getUserDate(userName!!,password!!)
                        onewayResponse ?.let {
                            Auth?.onSucsess(it!!)
                        }

                    }catch (e: ApiExaptions){
                        e.message?.let { Auth?.onFailer(it) }

                    }catch (e: noInternetExeption){
                        e.message?.let { Auth?.onFailer(it) }}

                }

            }



}
