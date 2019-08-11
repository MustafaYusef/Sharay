package com.mustafayusef.sharay.ui.add

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import okhttp3.MultipartBody
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException

class AddFragmentViewModel(val repostary: CarsRepostary) : ViewModel() {


    var Auth:AddCarLesener?=null
    fun AddCar( title: String,
                brand: String,
                `class`: String,
                status: String,
                year: Int,
                warid: String,
                mileage: Int,
                price: Int,
                gear: String,
                cylinders: Int,
                fuel:String,
                driveSystem: String,
                roof: String,
                seats: Int,
                type: String,
                window: String,
                airBags: String,
                color: String,
                description: String,
                name: String,
                phone: String,
                location: String,
                state: String,
                date: String,
                userId: Int,
                storeId: Int,
                active: Boolean,
                isRent: Boolean,
                isImported: Boolean,
                image: MultipartBody.Part){
        Auth?.OnStart()

        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.AddCar( title,
                    brand,
                    `class`,
                    status,
                    year,
                    warid,
                    mileage,
                    price,
                    gear,
                    cylinders,
                    fuel,
                    driveSystem,
                    roof,
                    seats,
                    type,
                    window,
                    airBags,
                    color,
                    description,
                    name,
                    phone,
                    location,
                    state,
                    date,
                    userId,
                    storeId,
                    active,
                    isRent,
                    isImported,
                    image)
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

    fun addImages(images:MutableList<MultipartBody.Part>,id:Int){

            Auth?.OnStartAddImages()

            corurtins.main {
                try {
                    val CarsDetailsResponse=repostary.images (images,id)
                    CarsDetailsResponse ?.let {it1->



                        Auth?.onSucsessAddImages(it1.data)
                    }

                }catch (e: ApiExaptions){
                    e.message?.let { Auth?.OnFailerAddImages(it) }

                }catch (e: noInternetExeption){
                    e.message?.let { Auth?.OnFailerAddImages(it) }
                }catch (e: SocketTimeoutException){
                    e.message?.let { Auth?.OnFailerAddImages(it) }}



        }
    }
}
