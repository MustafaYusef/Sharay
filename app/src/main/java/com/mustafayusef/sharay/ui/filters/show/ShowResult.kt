package com.mustafayusef.sharay.ui.filters.show

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.ui.sections.adapters.CarSaleAdapter
import kotlinx.android.synthetic.main.filter_fragment.*
import kotlinx.android.synthetic.main.show_result_fragment.*

class ShowResult : Fragment(),CarSaleAdapter.OnNoteLisener {

    companion object {
        fun newInstance() = ShowResult()
    }

    var responseCars:List<DataCars>?=null
    private lateinit var viewModel: ShowResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowResultViewModel::class.java)


        val type:CarsModel? = arguments?.getSerializable("Cars") as CarsModel?
        responseCars= type?.data
        if(responseCars!!.isNullOrEmpty()){
            noNetContainerFilter?.visibility=View.VISIBLE
        }

        FilterCar?.layoutManager= LinearLayoutManager(context)

        FilterCar?.adapter= context?.let { responseCars?.let { it1 -> CarSaleAdapter(it, this, it1) } }

    }

    override fun onNoteClick(position: Int) {
        val carId= responseCars?.get(position)!!.id
        //val carId= use?.Cars?.get (position)!!.id
        var bundle = bundleOf("carId" to carId)
        view?.findNavController()?.navigate(R.id.carDetails,bundle)
        val navBar = activity?.findViewById<BottomNavigationView> (R.id.bottomNav)
        val toolbar = activity?.findViewById<Toolbar> (R.id.ToolBar)

        view?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.carDetails) {
                //navBar?.visibility = View.GONE
                toolbar?.visibility = View.GONE

            } else {
                // navBar?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
            }
        }
    }
}
