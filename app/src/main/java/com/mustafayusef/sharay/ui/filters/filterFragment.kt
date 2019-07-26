package com.mustafayusef.sharay.ui.filters

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.mustafayusef.sharay.R
import kotlinx.android.synthetic.main.filter_fragment.*

class filterFragment : Fragment() {

    companion object {
        fun newInstance() = filterFragment()
    }

    private lateinit var viewModel: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)
        // TODO: Use the ViewModel
        closeFilter.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

}
