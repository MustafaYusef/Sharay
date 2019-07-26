package com.mustafayusef.sharay.ui.sections.carRent

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary

class CareRentViewModel (val repostary: SectionRepostary): ViewModel() {

    var lesener:rentLesener?=null

    fun GetRent(){
        lesener?.OnStart()

        corurtins.main {
            try {
                val CarsResponse=repostary.getCars()
                CarsResponse ?.let {
                    lesener?.onSucsess(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { lesener?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { lesener?.onFailer(it) }}

        }
    }
}
