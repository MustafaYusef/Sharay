package com.mustafayusef.sharay.ui.main.cardetails

import androidx.lifecycle.ViewModel
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.ui.main.MainCarLesener
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class CarDetailsViewModel(
    val repostary: CarsRepostary
) : ViewModel() {
    var CarImages: List<String>?=null
    var ClassCar: String?=null
   // var active: Boolean?=null
    var airBags: String?=null
    var brand: String?=null
    var camera: String?=null
    var color: String?=null
    var cylinders: String?=null
    var date: String?=null
    var description: String?=null
    var doors: String?=null
    var driveSystem: String?=null
    var fuel: String?=null
    var gear: String?=null
    var horse: String?=null
    var id: Int?=null
    var image: String?=null
    var isUsed: String?=null
    var lamps: String?=null
    var location: String?=null
    var mileage: String?=null
    var model: String?=null
    var name: String?=null
    var phone: String?=null
    var price: String?=null
    var roof: String?=null
    var seats: String?=null
    var sensors: String?=null
    var status: String?=null
    var storeId: Int?=null
    var title: String?=null
    var turbo: String?=null
    var type: String?=null
    var userId: String?=null
    var warid: String?=null
    var wheelSize: String?=null
    var window: String?=null
    var year: String?=null
  //var it:carDetails?=null

    var Auth: MainCarLesener?=null
    var Fav: FavCarLesener?=null


    fun GetDetailsCars(id:Int){
        Auth?.OnStart()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.getDetailsCars(id)
                CarsDetailsResponse ?.let {it1->



                    Auth?.onSucsessDetails(it1.data?.get(0)!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailer(it) }}

        }

    }

    fun AddFav(token:String,id:Int,type:Int){
        Fav?.OnStartFav()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.AddFavorite(token,id,type)
                CarsDetailsResponse ?.let {it1->



                    Fav?.onSucsessFav(it1)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Fav?.onFailerFav(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Fav?.onFailerFav(it) }
            } catch (e: SocketTimeoutException){
                e.message?.let { Fav?.onFailerFav(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailer(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailer(it) }
            }

        }

    }

    fun  DeleteFavorite(token:String,UserId:Int,CarId:Int){
        Fav?.OnStartFav()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.deleteFav(token,UserId,CarId)
                CarsDetailsResponse ?.let {it1->



                 Fav?.onSucsessDel(it1)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Fav?.onFailerFav(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Fav?.onFailerFav(it) }
            } catch (e: SocketTimeoutException){
                e.message?.let { Fav?.onFailerFav(it) }}

        }

    }

    fun profile(token:String){
        Fav?.OnStartProfile()

        corurtins.main {
            try {
                val onewayResponse=repostary.getUserIn(token)
                onewayResponse.Favorites ?.let {
                    Fav?.onSucsessProfile(it!!)
                }

            }catch (e: ApiExaptions){
                e.message?.let { Fav?.onFailerProfile(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Fav?.onFailerProfile(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Fav?.onFailerProfile(it) }}

        }

    }


}
