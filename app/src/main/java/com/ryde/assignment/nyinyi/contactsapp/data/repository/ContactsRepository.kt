package com.ryde.assignment.nyinyi.contactsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ryde.assignment.nyinyi.contactsapp.data.remote.api.ContactsApi
import com.ryde.assignment.nyinyi.contactsapp.data.local.db.AppDataBase
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.remote.remotediator.ContactsRemoteMediator
import com.ryde.assignment.nyinyi.contactsapp.models.ContactUploadRequest
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

//@Singleton
class ContactsRepository @Inject constructor(
    private val contactsApi: ContactsApi,
    private val db: AppDataBase
) {

    private val contactsDao = db.contactsDao
    private val pagingSourceFactory = { contactsDao.getContacts() }

    @ExperimentalPagingApi
    fun getContacts(): Flow<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ContactsRemoteMediator(
                contactsApi,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow //can also return livedata
    }

    fun get(id: String): LiveData<Contact> {
        return contactsDao.getContact(id)
    }

    suspend fun getSync(id: String): Contact {
        return contactsDao.getContactSync(id)
    }

    suspend fun create(firstName: String, lastName: String) {
        val request = ContactUploadRequest(name = firstName + lastName)
        val response = contactsApi.create(request)
        contactsDao.insert(
            Contact(
                id = UUID.randomUUID().toString(),
                email = "",
                first_name = firstName,
                last_name = lastName,
                avatar = "",
                hasModifiedLocally = true
            )
        )
    }

    suspend fun update(contact: Contact) {
        val request = ContactUploadRequest(name = contact.first_name + contact.last_name)
        val response = contactsApi.update(request)
        contactsDao.update(contact.copy(hasModifiedLocally = true))
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

}