package com.mustafayusef.sharay.ui.auth.signin

import android.view.View
import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.auth.AuthLesener
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class SignUpUserViewModel(val repostary: userRepostary) : ViewModel() {

    var email:String?=null
    var password:String?=null
    var phone:String?=null
    var name:String?=null
    var phoneSecond:String?=null
    var Auth: AuthLesener?=null

    fun SignIn(view: View){
        if(phoneSecond.isNullOrEmpty()){
            phoneSecond=null
        }
        if(email.isNullOrEmpty()){
            email=null
        }
        Auth?.OnStart()
        if(password.isNullOrEmpty()||phone.isNullOrEmpty()||name.isNullOrEmpty()
            ){
            Auth?.onFailerIn()
            return
        }else if(phone!!.length<10){
            Auth?.onFailerPh()
            return
        }else if(password!!.length<4){
            Auth?.onFailerPas()
            return
        }
        corurtins.main {
            try {
                val onewayResponse=repostary.getSignUpDate(email,password!!,phone!!,phoneSecond,name!!)
                onewayResponse ?.let {
                    Auth?.onSucsessSignIn(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet() }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet() }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet() }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet() }
            }

        }

    }
}
