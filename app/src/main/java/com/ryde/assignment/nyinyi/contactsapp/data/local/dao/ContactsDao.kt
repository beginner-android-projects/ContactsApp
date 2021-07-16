package com.ryde.assignment.nyinyi.contactsapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleContacts(list: List<Contact>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update()
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contacts_table")
    fun getContacts(): PagingSource<Int, Contact>

    @Query("SELECT * FROM contacts_table WHERE id = :id")
    fun getContact(id: String): LiveData<Contact>

    @Query("SELECT * FROM contacts_table WHERE id = :id")
    suspend fun getContactSync(id: String): Contact

    @Query("DELETE FROM contacts_table")
    suspend fun clearRepos()

    @Query("SELECT COUNT(id) from contacts_table")
    suspend fun count(): Int

}