package com.ryde.assignment.nyinyi.contactsapp.navigator

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.ui.contact.list.ContactListFragment
import javax.inject.Inject

class ApplicationNavigatorImpl @Inject constructor(private val activity : FragmentActivity) : ApplicationNavigator {
    override fun navigateTo(screen: Screens, args: Bundle?) {

        val fragment = when (screen) {
            Screens.CONTACT_LIST -> ContactListFragment()
            Screens.CONTACT_DETAIL -> ContactListFragment()
            Screens.CONTACT_EDIT -> ContactListFragment()
            Screens.CONTACT_CREATE -> ContactListFragment()
        }.apply {
            arguments = args
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

}