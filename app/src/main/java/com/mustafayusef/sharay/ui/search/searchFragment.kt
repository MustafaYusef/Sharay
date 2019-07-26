package com.mustafayusef.sharay.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.sharay.R
import com.mustafayusef.sharay.databinding.SearchFragmentBinding

import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import kotlinx.android.synthetic.main.search_fragment.*

class searchFragment : Fragment(),lastSearchAdapter.OnNoteLisener,searchLesener,searchResultAdapter.OnNoteLisener {



    companion object {
        fun newInstance() = searchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        var binding:SearchFragmentBinding=
            DataBindingUtil.inflate(inflater,R.layout.search_fragment,container,false)
        viewModel=ViewModelProviders.of(this).get(SearchViewModel::class.java)
        binding?.viewmodel=viewModel

        viewModel?.litsener=this
        binding?.executePendingBindings()
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        closeSearch.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        SearchLastList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)

        SearchLastList.adapter= context?.let { lastSearchAdapter(it,this) }
    }
    override fun onNoteClick(position: Int) {
      context?.toast(position.toString())
    }

    override fun onFailerSerach(message: String) {
        context?.toast(message)
    }

    override fun onSuccessSearch() {
        list_of_search_cars?.setHasFixedSize(true)

        list_of_search_cars?.isDrawingCacheEnabled = true
        list_of_search_cars?.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        list_of_search_cars?.isNestedScrollingEnabled = false


        list_of_search_cars?.layoutManager= LinearLayoutManager(context)
        list_of_search_cars?.adapter= context?.let { searchResultAdapter(it ,this) }
        lastContainer.visibility=View.GONE
    }

    override fun onStartSearch() {
        context?.toast("start search")
    }
}
