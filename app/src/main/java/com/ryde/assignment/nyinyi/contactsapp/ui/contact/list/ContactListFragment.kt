package com.ryde.assignment.nyinyi.contactsapp.ui.contact.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.data.local.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.databinding.FragmentContactListBinding
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.navigator.Screens
import com.ryde.assignment.nyinyi.contactsapp.ui.adapter.ContactAdapter
import com.ryde.assignment.nyinyi.contactsapp.ui.adapter.ContactLoadingStateAdapter
import com.ryde.assignment.nyinyi.contactsapp.utils.RecyclerViewItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    private val viewModel: ContactListViewModel by viewModels()

    @Inject
    lateinit var navigator: ApplicationNavigator

    private var _binding: FragmentContactListBinding? = null

    private val binding get() = _binding!!

    private val adapter = ContactAdapter { contact: Contact ->
        navigator.navigateTo(Screens.CONTACT_DETAIL, bundleOf("id" to contact.id))
    }

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

        setUpAdapter()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.btnRetry.setOnClickListener {
            retry()
        }

        viewModel.contacts.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setUpAdapter() {

        binding.allProductRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                (layoutManager as LinearLayoutManager).orientation
            )
            this.addItemDecoration(dividerItemDecoration)
        }
        binding.allProductRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = ContactLoadingStateAdapter { retry() }
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->

                if (loadStates.refresh !is LoadState.Loading) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                binding.progress.isVisible =
                    loadStates.refresh is LoadState.Loading && adapter.snapshot().isEmpty()

                val isLoading =
                    loadStates.refresh is LoadState.Error && adapter.snapshot().isEmpty()
                binding.errorTxt.isVisible = isLoading
                binding.btnRetry.isVisible = isLoading
                (loadStates.refresh as? LoadState.Error)?.let { refreshError ->
                    binding.errorTxt.text = refreshError.error.message
                }
            }
        }
    }

    private fun retry() {
        adapter.retry()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}