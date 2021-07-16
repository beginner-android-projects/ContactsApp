package com.ryde.assignment.nyinyi.contactsapp.ui.contact.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryde.assignment.nyinyi.contactsapp.data.repository.ContactsRepository
import com.ryde.assignment.nyinyi.contactsapp.utils.Event
import com.ryde.assignment.nyinyi.contactsapp.utils.postEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactCreateViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> = _successEvent

    fun create(firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                repository.create(firstName, lastName)
                _successEvent.postEvent(Unit)
            } catch (e: Exception) {
                _error.value = e.message
                _errorEvent.postEvent(e.message.orEmpty())
            }
        }
    }

}