package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.room.model.Receipt
import com.my.project.firstkotlin.databinding.FragmentReceiptListBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.ReceiptListViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class ReceiptListFragment : BaseFragment() {

    private lateinit var binding : FragmentReceiptListBinding
    private lateinit var receiptListViewModel : ReceiptListViewModel

    override fun layoutId() = R.layout.fragment_receipt_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentReceiptListBinding.bind(view)

        val factory = ViewModelFactory(activity!!.application)

        receiptListViewModel = ViewModelProvider(this, factory).get(ReceiptListViewModel::class.java)

        binding.receiptViewModel = receiptListViewModel
        binding.lifecycleOwner = this

        setUpList()
    }

    private fun setUpList() {

        val adapter = RecipesAdapter {selectedReceipt : Receipt -> onItemClick(selectedReceipt)}
        val layoutManager = LinearLayoutManager(context)

        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.receiptsRecycler.adapter = adapter
        binding.receiptsRecycler.layoutManager = layoutManager
        binding.receiptsRecycler.setHasFixedSize(true)

        receiptListViewModel.getAllReceipts()?.observe(this, Observer {
            adapter.setReceiptsList(it)
        })

    }

    private fun onItemClick(receipt : Receipt) {

    }

}

