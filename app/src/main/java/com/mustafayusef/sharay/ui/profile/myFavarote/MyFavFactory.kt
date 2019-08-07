package com.mustafayusef.sharay.ui.profile.myFavarote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary

class MyFavFactory(val repostary: CarsRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyFavViewModel(repostary) as T
    }
}