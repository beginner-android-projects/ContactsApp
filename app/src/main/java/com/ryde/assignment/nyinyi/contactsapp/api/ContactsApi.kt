package com.ryde.assignment.nyinyi.contactsapp.api

import com.ryde.assignment.nyinyi.contactsapp.models.ContactData
import com.ryde.assignment.nyinyi.contactsapp.models.ContactUpload
import com.ryde.assignment.nyinyi.contactsapp.models.ContactUploadRequest
import retrofit2.http.*

interface ContactsApi {

    @GET("users")
    suspend fun getContacts(
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ): ContactData

    @PUT("users")
    suspend fun update(
        @Body request: ContactUploadRequest
    ): ContactUpload

    @POST("users")
    suspend fun create(
        @Body request: ContactUploadRequest
    ): ContactUpload

}