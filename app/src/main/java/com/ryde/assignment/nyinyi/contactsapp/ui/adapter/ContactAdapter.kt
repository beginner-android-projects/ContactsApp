package com.ryde.assignment.nyinyi.contactsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.databinding.AdapterItemBinding

class ContactAdapter(private val clicked: (Contact) -> Unit) :
    PagingDataAdapter<Contact, ContactAdapter.ContactsViewHolder>(ContactsDiffCallback()) {


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {

        return ContactsViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }


    inner class ContactsViewHolder(private val binding: AdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contact?) {

            binding.let {
                val name = it.root.context.getString(
                    R.string.name,
                    data?.first_name, data?.last_name
                )
                it.name.text = name

                if (data?.avatar.isNullOrEmpty()) {
                    it.ivUser.load(R.drawable.profile_placeholder) {
                        crossfade(true)
                        placeholder(R.drawable.bg_circle)
                        error(R.drawable.bg_circle)
                        transformations(CircleCropTransformation())
                    }

                } else {
                    it.ivUser.load(data?.avatar) {
                        crossfade(true)
                        placeholder(R.drawable.profile_placeholder)
                        error(R.drawable.profile_placeholder)
                        transformations(CircleCropTransformation())
                    }
                }


                it.root.setOnClickListener {
                    data?.let { it1 -> clicked.invoke(it1) }
                }
            }


        }
    }

    private class ContactsDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}