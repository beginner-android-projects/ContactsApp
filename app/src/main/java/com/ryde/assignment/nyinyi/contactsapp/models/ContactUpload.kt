package com.ryde.assignment.nyinyi.contactsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactUpload(
    val id: String,
    val createdAt: String
) : Parcelable

@Parcelize
data class ContactUploadRequest(
    val name: String
) : Parcelable