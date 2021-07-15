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

    private val selectedItemId = MutableLiveData<String>()
    val selectedItem: LiveData<Contact>
        get() = selectedItemId.switchMap {
            repository.get(it)
        }

    lateinit var contacts: LiveData<PagingData<Contact>>

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> = _successEvent

    init {
        viewModelScope.launch {
            contacts = repository.getContacts().cachedIn(viewModelScope).asLiveData()
        }
    }

    fun update(firstName: String, lastName: String) {
        selectedItemId.value?.let { contactId ->
            viewModelScope.launch {
                val contact = repository.getSync(contactId)
                try {
                    repository.update(contact.copy(first_name = firstName, last_name = lastName))
                    _successEvent.postEvent(Unit)
                } catch (e: Exception) {
                    _error.value = e.message
                    _errorEvent.postEvent(e.message.orEmpty())
                }
            }
        }
    }

}