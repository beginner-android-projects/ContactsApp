package com.ryde.assignment.nyinyi.contactsapp.navigator

import android.os.Bundle

enum class Screens {
    CONTACT_LIST,
    CONTACT_DETAIL,
    CONTACT_EDIT,
    CONTACT_CREATE
}

interface ApplicationNavigator {
    fun navigateTo(screen: Screens, args: Bundle? = null)
}