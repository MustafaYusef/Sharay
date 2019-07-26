package com.mustafayusef.sharay.ui.main.cardetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary

class DetailsCarViewModelFactory( val repostary: CarsRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CarDetailsViewModel(repostary) as T
    }
}