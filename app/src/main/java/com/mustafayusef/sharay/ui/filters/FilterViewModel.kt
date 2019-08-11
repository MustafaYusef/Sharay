package com.mustafayusef.sharay.ui.filters

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class FilterViewModel(var repostary: CarsRepostary) : ViewModel() {

  var Auth:FilterCarLesener?=null


    fun GetFiltersCars(brand: String?,
                       `class`: String?,
                       year: String?,
                       warid: String?,
                       mileage: String?,
                       priceMin: String?,
                       priceMax:String?,
                       gear: String?){
        Auth?.OnStart()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.getFilter(brand,`class`,year,warid,mileage,priceMin,priceMax,gear)
                CarsDetailsResponse ?.let {it1->



                    Auth?.onSucsess(it1!!)
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
