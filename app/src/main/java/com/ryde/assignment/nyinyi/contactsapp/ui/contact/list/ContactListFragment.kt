package com.ryde.assignment.nyinyi.contactsapp.ui.contact.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.databinding.FragmentContactListBinding
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    @Inject
    lateinit var navigator: ApplicationNavigator

    private var _binding: FragmentContactListBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactListBinding.bind(view)
        binding.toolbar.inflateMenu(R.menu.menu_item_home)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add -> {
                    navigator.navigateTo(Screens.CONTACT_CREATE)
                    true
                }
                else -> false
            }
        }

    }

}