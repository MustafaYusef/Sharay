package com.mustafayusef.sharay.ui.profile

import android.view.View
import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary
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

        }

    }
}
