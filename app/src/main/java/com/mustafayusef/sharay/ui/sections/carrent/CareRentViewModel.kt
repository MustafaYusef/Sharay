package com.mustafayusef.sharay.ui.sections.carrent

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import java.net.ProtocolException
import java.net.SocketTimeoutException

class CareRentViewModel (val repostary: SectionRepostary): ViewModel() {

    var lesener:rentLesener?=null

    fun GetNumbers(){
        lesener?.OnStart()

        corurtins.main {
            try {

                val NumbersResponse=repostary.GetNumbers()

                NumbersResponse ?.let {
                    lesener?.onSucsessNumbers(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

    fun GetStores(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.GetStores()
                NumbersResponse ?.let {
                    lesener?.onSucsessStores(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

    fun GetRent(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.GetRent()
                NumbersResponse ?.let {
                    lesener?.onSucsessRent(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

    fun GetCars(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.getCars()
                NumbersResponse ?.let {
                    lesener?.onSucsessCars(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

    fun GetParts(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.GetParts()
                NumbersResponse ?.let {
                    lesener?.onSucsessParts(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

    fun GetMotor(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.GetMotors()
                NumbersResponse ?.let {
                    lesener?.onSucsessMotor(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }
    fun GetImport(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val NumbersResponse=repostary.GetImport()
                NumbersResponse ?.let {
                    lesener?.onSucsessImport(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.OnFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.OnFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.OnFailer(it) }}
            catch (e: ProtocolException){
                e.message?.let { lesener?.OnFailer(it) }
            }

        }
    }

}
