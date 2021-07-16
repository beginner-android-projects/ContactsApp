package com.ryde.assignment.nyinyi.contactsapp.ui.contact.create

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.databinding.FragmentContactCreateBinding
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactCreateFragment : Fragment(R.layout.fragment_contact_create) {

    private val viewModel: ContactCreateViewModel by viewModels()

    @Inject
    lateinit var navigator: ApplicationNavigator

    private var _binding: FragmentContactCreateBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactCreateBinding.bind(view)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.ivUser.load(R.drawable.profile_placeholder) {
            crossfade(true)
            placeholder(R.drawable.bg_circle)
            error(R.drawable.bg_circle)
            transformations(CircleCropTransformation())
        }

        binding.btnSave.setOnClickListener {
            val firstName = binding.outlinedTextFieldFirstName.editText?.text.toString()
            val lastName = binding.outlinedTextFieldLastName.editText?.text.toString()
            binding.progress?.isVisible = true
            viewModel.create(firstName, lastName)
        }

        viewModel.successEvent.observeEvent(viewLifecycleOwner) {
            binding.progress?.isVisible = false
            activity?.onBackPressed()
        }

        viewModel.errorEvent.observeEvent(viewLifecycleOwner) {
            binding.progress?.isVisible = false
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(it)
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}