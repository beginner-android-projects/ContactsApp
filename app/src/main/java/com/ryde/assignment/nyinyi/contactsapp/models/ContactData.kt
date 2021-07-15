package com.ryde.assignment.nyinyi.contactsapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactData(

    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    @SerializedName("data")
    val data: List<Contact>,
    val support: Support
) : Parcelable