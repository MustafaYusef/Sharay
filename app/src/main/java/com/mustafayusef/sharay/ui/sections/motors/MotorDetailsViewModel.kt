package com.mustafayusef.sharay.ui.sections.motors

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import java.net.SocketTimeoutException

class MotorDetailsViewModel(val repostary: SectionRepostary) : ViewModel() {
    var lesener:motorDLesener?=null
    fun GetDetailsMotor(id:Int){
        lesener?.OnStart()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.GetMotorDetails (id)
                CarsDetailsResponse ?.let {it1->



                    lesener?.onSucsess(it1.data)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.onFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {lesener?.onFailer(it) }}

        }

    }
}
