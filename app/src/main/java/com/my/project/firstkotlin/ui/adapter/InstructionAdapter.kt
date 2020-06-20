package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Instruction
import com.my.project.firstkotlin.databinding.ItemInstructionBinding

class InstructionAdapter (private var stepsList : List<Instruction.Step?>) : RecyclerView.Adapter<InstructionAdapter.ItemInstructViewHolder>(){

    class ItemInstructViewHolder (private val binding : ItemInstructionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (step : Instruction.Step) {
            binding.stepItem = step
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemInstructViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemInstructionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_instruction, parent, false)
        return ItemInstructViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemInstructViewHolder, position: Int) {
        stepsList[position]?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = stepsList.size

}