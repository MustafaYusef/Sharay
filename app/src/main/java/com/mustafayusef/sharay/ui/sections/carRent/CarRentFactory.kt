package com.mustafayusef.sharay.ui.sections.carRent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.CarsRepostary
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary
import com.mustafayusef.sharay.ui.main.MainFragmentViewModel

class CarRentFactory( val repostary: SectionRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CareRentViewModel(repostary) as T
    }
}