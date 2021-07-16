package com.ryde.assignment.nyinyi.contactsapp.ui.contact.list

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    lateinit var contacts: LiveData<PagingData<Contact>>

    init {
        viewModelScope.launch {
            contacts = repository.getContacts().cachedIn(viewModelScope).asLiveData()
        }
    }


}