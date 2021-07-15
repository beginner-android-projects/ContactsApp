package com.ryde.assignment.nyinyi.contactsapp.ui.contact.detail

import androidx.lifecycle.*
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val repository: ContactsRepository
): ViewModel() {

    private val selectedItemId = MutableLiveData<String>()
    val selectedItem: LiveData<Contact>
        get() = selectedItemId.distinctUntilChanged().switchMap {
        repository.get(it)
    }

    fun selectItem(id: String) {
        selectedItemId.value = id
    }
}