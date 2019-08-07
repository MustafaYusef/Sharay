package com.mustafayusef.sharay.ui.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary

class FiltersCarViewModelFactory(val repostary: CarsRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilterViewModel(repostary) as T
    }
}