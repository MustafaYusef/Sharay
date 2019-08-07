package com.mustafayusef.sharay.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary

class AddCarFactory(val repostary: CarsRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddFragmentViewModel(repostary) as T
    }
}