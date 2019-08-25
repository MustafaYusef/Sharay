package com.mustafayusef.sharay.ui.auth.signup

import android.view.View
import androidx.lifecycle.ViewModel
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import com.mustafayusef.sharay.ui.auth.AuthLesener
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class LoginViewModel(val repostary: userRepostary) : ViewModel() {

    var phone:String?=null
    var password:String?=null
       var Auth: AuthLesener?=null

    fun Login(view: View){
        Auth?.OnStart()
        if(phone.isNullOrEmpty()||password.isNullOrEmpty()){
            Auth?.onFailerIn()
            return
        }
                corurtins.main {
                    try {
                        val onewayResponse=repostary.getUserDate(phone!!,password!!)
                        onewayResponse ?.let {
                            Auth?.onSucsess(it!!)
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
