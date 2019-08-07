package com.mustafayusef.sharay.ui.main

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import java.net.SocketTimeoutException

class MainFragmentViewModel(val repostary: CarsRepostary) : ViewModel() {
    var Auth: MainCarLesener?=null

    var carData: CarsModel?=null
    fun GetCars(){
        Auth?.OnStart()

        corurtins.main {
            try {
                val CarsResponse=repostary.getCars()
                CarsResponse ?.let {
                    Auth?.onSucsess(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }
            } catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailer(it) }}

        }

    }

    fun getBanners(){
        Auth?.OnStart()
 corurtins.main {
            try {
                val bannerResponse=repostary.getBanners()
                bannerResponse ?.let {
                    Auth?.onSucsessBanners(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }
            } catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailer(it) }}

        }

    }
}
