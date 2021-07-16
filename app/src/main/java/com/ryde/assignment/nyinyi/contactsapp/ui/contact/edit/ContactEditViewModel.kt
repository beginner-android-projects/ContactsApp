package com.ryde.assignment.nyinyi.contactsapp.ui.contact.edit

import androidx.lifecycle.*
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.repository.ContactsRepository
import com.ryde.assignment.nyinyi.contactsapp.utils.Event
import com.ryde.assignment.nyinyi.contactsapp.utils.postEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactEditViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val selectedItemId = MutableLiveData<String>()
    val selectedItem: LiveData<Contact> get() = selectedItemId.switchMap {
        repository.get(it)
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> = _successEvent

    fun selectItem(id: String) {
        selectedItemId.value = id
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