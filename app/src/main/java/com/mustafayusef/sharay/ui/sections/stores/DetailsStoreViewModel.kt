package com.mustafayusef.sharay.ui.sections.stores

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import java.net.SocketTimeoutException

class DetailsStoreViewModel(val repostary: SectionRepostary) : ViewModel() {
     var litsener:storeLesener?=null
    fun storeDetails(id:Int) {
        litsener?.OnStart()
        corurtins.main {
            try {
                val NumbersResponse = repostary.GetStoreDetails(id)
                NumbersResponse?.let {

                    litsener?.onSeccess(NumbersResponse)
                }

            } catch (e: ApiExaptions) {
                e.message?.let { litsener?.onFailer(it) }

            } catch (e: noInternetExeption) {
                e.message?.let { litsener?.onFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {litsener?.onFailer(it) }}


        }
    }

}
