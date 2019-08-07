package com.mustafayusef.sharay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsSearchRepostary
import com.mustafayusef.sharay.database.databaseApp

class CarSearchFactory(val repostary: CarsSearchRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repostary) as T
    }
}