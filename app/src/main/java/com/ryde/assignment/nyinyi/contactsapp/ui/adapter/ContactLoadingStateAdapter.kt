package com.ryde.assignment.nyinyi.contactsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryde.assignment.nyinyi.contactsapp.databinding.NetworkStateItemBinding

class ContactLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ContactLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.binding.progressBarItem
        val retryBtn = holder.binding.retyBtn
        val txtErrorMessage = holder.binding.errorMsgItem

        if (loadState is LoadState.Loading) {
            progress.isVisible = true
            txtErrorMessage.isVisible = false
            retryBtn.isVisible = false

        } else {
            progress.isVisible = false
        }

        if (loadState is LoadState.Error) {
            txtErrorMessage.isVisible = true
            retryBtn.isVisible = true
            txtErrorMessage.text = loadState.error.localizedMessage
        }


        retryBtn.setOnClickListener {
            retry.invoke()
        }
    }

    inner class LoadStateViewHolder(val binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}