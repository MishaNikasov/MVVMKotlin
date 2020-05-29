package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.FragmentReceiptListBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.viewmodel.ReceiptViewModel
import com.my.project.firstkotlin.viewmodel.ReceiptViewModelFactory

class ReceiptListFragment : BaseFragment() {

    private lateinit var binding: FragmentReceiptListBinding
    private lateinit var receiptViewModel : ReceiptViewModel
    private var adapter : RecipesAdapter? = null

    override fun layoutId() = R.layout.fragment_receipt_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentReceiptListBinding.bind(view)

        val factory = ReceiptViewModelFactory(activity!!.application)

        receiptViewModel = ViewModelProvider(this, factory).get(ReceiptViewModel::class.java)
        binding.receiptViewModel = receiptViewModel
        binding.lifecycleOwner = this

        setUpList()
    }

    private fun setUpList() {

        adapter = RecipesAdapter()

        binding.receiptsRecycler.adapter = adapter
        binding.receiptsRecycler.layoutManager = LinearLayoutManager(context)
        binding.receiptsRecycler.setHasFixedSize(true)

        receiptViewModel.getAllReceipts()?.observe(this, Observer {
            adapter?.setReceiptsList(it)
            Log.i("TAAG", it.toString())
        })

    }

}

