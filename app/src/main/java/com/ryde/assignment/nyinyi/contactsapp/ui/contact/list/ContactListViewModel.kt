package com.ryde.assignment.nyinyi.contactsapp.ui.contact.list

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.repository.ContactsRepository
import com.ryde.assignment.nyinyi.contactsapp.utils.Event
import com.ryde.assignment.nyinyi.contactsapp.utils.postEvent
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