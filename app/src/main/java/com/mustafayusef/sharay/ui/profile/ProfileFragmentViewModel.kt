package com.mustafayusef.sharay.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class ProfileFragmentViewModel(val repostary: userRepostary) : ViewModel() {

    var Auth:profileLesener?=null

    fun profile(token:String){
        Auth?.OnStart()

        corurtins.main {
            try {
                val onewayResponse=repostary.getUserInfo(token)
                onewayResponse ?.let {
                    Auth?.onSucsess(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailer(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailer(it) }
            }

        }

    }

    fun DeletMyCar(token:String,carId:Int){
        Auth?.OnStartDeletCar()

        corurtins.main {
            try {
                val onewayResponse=repostary.deletMyCar(token,carId)
                onewayResponse ?.let {
                    Auth?.onSucsessDeletCar(it)          }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailerDeletCar(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerDeletCar(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerDeletCar(it) }}

        }

    }
    fun Login(phone:String,password:String){
        Auth?.OnStart()
        corurtins.main {
            try {
                val onewayResponse=repostary.getUserDate(phone!!,password!!)
                onewayResponse ?.let {
                    Auth?.onSucsessLog(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailer(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailer(it) }
            }

        }

    }
}
