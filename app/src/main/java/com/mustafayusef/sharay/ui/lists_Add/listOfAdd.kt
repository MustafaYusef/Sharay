package com.mustafayusef.sharay.ui.lists_Add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.list_of_add_fragment.*
import android.text.Editable
import android.text.TextWatcher
import android.R
import com.mustafayusef.sharay.ui.LocaleHelper
import com.mustafayusef.sharay.ui.MainActivity
import androidx.recyclerview.widget.DividerItemDecoration




class listOfAdd : Fragment(),ListAddAdapter.OnNoteLisener {
    override fun onNoteClick(position: Int) {

    }
    var temp:MutableList<String> = arrayListOf()
    var arrayOfMarks=arrayOf("hyondai","toyota","isus","chevrolet")

    companion object {
        fun newInstance() = listOfAdd()
    }

    private lateinit var viewModel: ListOfAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(MainActivity.cacheObj.language!=""){
            context?.let { it1 -> LocaleHelper.setLocale(it1, MainActivity.cacheObj.language ) }

        }
        return inflater.inflate(com.mustafayusef.sharay.R.layout.list_of_add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListOfAddViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        markeList.layoutManager=LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            markeList.getContext(),
            (markeList.layoutManager as LinearLayoutManager).getOrientation()
        )
        markeList.addItemDecoration(dividerItemDecoration)
        var adapterList=context?.let { ListAddAdapter(it,this,arrayOfMarks) }
        markeList.adapter= adapterList
        searchIcon.setOnClickListener {
            filter(searchList.text.toString())
            adapterList?.updateList(temp)
            markeList.adapter?.notifyDataSetChanged()

        }
        searchList.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                filter(s.toString())
                adapterList?.updateList(temp)
                markeList.adapter?.notifyDataSetChanged()

                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                // filter your list from your input
                filter(s.toString())
                adapterList?.updateList(temp)
                markeList.adapter?.notifyDataSetChanged()

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })

    }
    fun filter(text:String){
        temp.clear()
        for(te in arrayOfMarks){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(te.contains(text.toLowerCase())){
                temp.add(te)
            }
        }

        //update recyclerview
//        markeList.layoutManager=LinearLayoutManager(context)
//        markeList.adapter.up
    }
}
