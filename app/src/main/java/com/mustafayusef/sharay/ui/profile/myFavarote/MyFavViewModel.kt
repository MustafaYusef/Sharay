package com.mustafayusef.sharay.ui.profile.myFavarote

import androidx.lifecycle.ViewModel
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class MyFavViewModel(val repostary: CarsRepostary) : ViewModel() {


    //var Auth: MainCarLesener?=null
    var Fav: MyFavLesener?=null


    fun GetFavorite(token:String){
        Fav?.OnStartFav()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.GetFavorite(token)
                CarsDetailsResponse ?.let {it1->



                    Fav?.onSucsessFav(it1)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Fav?.onFailerFav(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Fav?.onFailerFav(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Fav?.onFailerFav(it) }}
            catch (e: SocketException){
                e.message?.let { Fav?.onFailerFav(it) }
            }catch (e: ProtocolException){
                e.message?.let { Fav?.onFailerFav(it) }
            }

        }

    }



}
