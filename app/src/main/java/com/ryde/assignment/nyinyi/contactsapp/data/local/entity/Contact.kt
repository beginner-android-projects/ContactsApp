package com.ryde.assignment.nyinyi.contactsapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contacts_table")
data class Contact(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,
    val hasModifiedLocally: Boolean? = false
) : Parcelable