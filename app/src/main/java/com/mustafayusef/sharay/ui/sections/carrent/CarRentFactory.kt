package com.mustafayusef.sharay.ui.sections.carrent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary

class CarRentFactory( val repostary: SectionRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CareRentViewModel(repostary) as T
    }
}