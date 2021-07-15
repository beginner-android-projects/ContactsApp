package com.ryde.assignment.nyinyi.contactsapp.ui.contact.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.databinding.FragmentContactDetailBinding
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.navigator.Screens
import com.ryde.assignment.nyinyi.contactsapp.utils.setReadOnly
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {

    private val viewModel: ContactDetailViewModel by viewModels()

    @Inject
    lateinit var navigator: ApplicationNavigator

    private var _binding: FragmentContactDetailBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("id")?.also {
            viewModel.selectItem(it)
        }

        _binding = FragmentContactDetailBinding.bind(view)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        bindProfileData()

    }

    private fun bindProfileData() {

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer { contact ->
            binding.let {

                if (contact?.avatar.isNullOrEmpty()) {
                    it.ivUser.load(R.drawable.profile_placeholder) {
                        crossfade(true)
                        placeholder(R.drawable.bg_circle)
                        error(R.drawable.bg_circle)
                        transformations(CircleCropTransformation())
                    }

                } else {
                    it.ivUser.load(contact.avatar) {
                        crossfade(true)
                        placeholder(R.drawable.bg_circle)
                        error(R.drawable.bg_circle)
                        transformations(CircleCropTransformation())
                    }
                }

                val name = it.root.context.getString(
                    R.string.name,
                    contact?.first_name, contact?.last_name
                )
                it.name.text = name

                it.outlinedTextFieldEmail.editText?.setReadOnly(true)
                it.outlinedTextFieldMobile.editText?.setReadOnly(true)

                it.outlinedTextFieldMobile.editText?.setText(contact?.id.toString())
                it.outlinedTextFieldEmail.editText?.setText(contact?.email)

                it.btnEdit.setOnClickListener {
                    navigator.navigateTo(Screens.CONTACT_EDIT, bundleOf("id" to contact?.id))
                }
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}