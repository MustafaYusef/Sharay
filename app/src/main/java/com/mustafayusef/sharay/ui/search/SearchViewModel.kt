package com.mustafayusef.sharay.ui.search

import androidx.lifecycle.ViewModel;
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.corurtins
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.networks.repostorys.CarsSearchRepostary
import com.mustafayusef.sharay.database.entitis.latestCar
import io.reactivex.Completable
import java.net.SocketTimeoutException

class SearchViewModel(val repostary: CarsSearchRepostary) : ViewModel() {
    val filteredPosts: MutableList<DataCars> = mutableListOf()
    val oldFilteredPosts: MutableList<DataCars> = mutableListOf()
    lateinit var originalPosts:List<DataCars>
//    init {
//        oldFilteredPosts.addAll(originalPosts)
//    }


    var litsener: searchLesener? = null
    fun doSearch() {
        litsener?.onStartSearch()
        corurtins.main {
            try {
                val NumbersResponse = repostary.getCars()
                NumbersResponse?.let {
                    originalPosts=it.data
                    litsener?.onSuccessSearch(NumbersResponse)
                }

            } catch (e: ApiExaptions) {
                e.message?.let { litsener?.onFailerSerach(it) }

            } catch (e: noInternetExeption) {
                e.message?.let { litsener?.onFailerSerach(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let {litsener?.onFailerSerach(it) }}


        }
    }

    fun getData() {

        corurtins.main {
            try {
                val NumbersResponse = repostary.getLatest()
                NumbersResponse?.let {
                    litsener?.onSuccessDatabase(NumbersResponse)
                }

            } catch (e: ApiExaptions) {
                e.message?.let { litsener?.onFailerSerach(it) }

            }


        }
    }

    fun saveData(car: latestCar?) {

        corurtins.main {
            try {
                val NumbersResponse = car?.let { repostary.saveData(it) }
                NumbersResponse?.let {
                    litsener?.onSuccessDatabasesave("save success "+it)
                }

            } catch (e: ApiExaptions) {
                e.message?.let { litsener?.onFailerSerach(it) }

            }


        }

        corurtins.main {
            try {
                val NumbersResponse = repostary.deletLast()
                NumbersResponse?.let {
                    litsener?.onSuccessDatabasesave("delete success "+it)
                }

            } catch (e: ApiExaptions) {
                e.message?.let { litsener?.onFailerSerach(it) }

            }


        }

    }
//    fun search(query: String): Completable = Completable.create {
//        val wanted = originalPosts.filter {
//            it.title.contains(query)
//        }.toList()
//
//        filteredPosts.clear()
//        filteredPosts.addAll(wanted)
//        it.onComplete()
//    }
}
