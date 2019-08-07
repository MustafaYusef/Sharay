package com.mustafayusef.sharay.ui.sections.motors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary

class motorDetailsFactory(val repostary: SectionRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MotorDetailsViewModel(repostary) as T
    }
}