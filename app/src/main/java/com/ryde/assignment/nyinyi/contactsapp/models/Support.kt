package com.ryde.assignment.nyinyi.contactsapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Support(

    val url: String,
    val text: String
) : Parcelable