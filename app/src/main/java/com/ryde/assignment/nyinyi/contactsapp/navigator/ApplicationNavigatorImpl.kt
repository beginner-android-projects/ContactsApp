package com.ryde.assignment.nyinyi.contactsapp.navigator

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.ui.contact.create.ContactCreateFragment
import com.ryde.assignment.nyinyi.contactsapp.ui.contact.detail.ContactDetailFragment
import com.ryde.assignment.nyinyi.contactsapp.ui.contact.edit.ContactEditFragment
import com.ryde.assignment.nyinyi.contactsapp.ui.contact.list.ContactListFragment
import javax.inject.Inject

class ApplicationNavigatorImpl @Inject constructor(private val activity: FragmentActivity) :
    ApplicationNavigator {
    override fun navigateTo(screen: Screens, args: Bundle?) {

        val fragment = when (screen) {
            Screens.CONTACT_LIST -> ContactListFragment()
            Screens.CONTACT_DETAIL -> ContactDetailFragment()
            Screens.CONTACT_EDIT -> ContactEditFragment()
            Screens.CONTACT_CREATE -> ContactCreateFragment()
        }.apply {
            arguments = args
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

}