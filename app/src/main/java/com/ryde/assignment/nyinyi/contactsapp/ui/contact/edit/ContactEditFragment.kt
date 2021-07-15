package com.ryde.assignment.nyinyi.contactsapp.ui.contact.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.databinding.FragmentContactEditBinding
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.utils.observeEvent
import com.ryde.assignment.nyinyi.contactsapp.utils.setReadOnly
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactEditFragment : Fragment(R.layout.fragment_contact_edit) {

    private val viewModel: ContactEditViewModel by viewModels()

    @Inject
    lateinit var navigator: ApplicationNavigator

    private var _binding: FragmentContactEditBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("id")?.also {
            viewModel.selectItem(it)
        }

        _binding = FragmentContactEditBinding.bind(view)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            val firstName = binding.outlinedTextFieldFirstName?.editText?.text.toString()
            val lastName = binding.outlinedTextFieldLastName?.editText?.text.toString()
            viewModel.update(firstName, lastName)
        }

        binding.ivUser.load(R.drawable.profile_placeholder) {
            crossfade(true)
            placeholder(R.drawable.bg_circle)
            error(R.drawable.bg_circle)
            transformations(CircleCropTransformation())
        }

        bindProfileData()

        viewModel.successEvent.observeEvent(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        viewModel.errorEvent.observeEvent(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(it)
                .show()
        }
    }

    private fun bindProfileData() {
        viewModel.selectedItem.observe(viewLifecycleOwner, Observer { contact ->
            binding.let {
                it.outlinedTextFieldFirstName?.editText?.setText(contact?.first_name)

                it.outlinedTextFieldLastName?.editText?.setText(contact?.last_name)

                it.outlinedTextFieldMobile?.editText?.setText(contact?.id.toString())
                it.outlinedTextFieldMobile?.editText?.setReadOnly(true)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}