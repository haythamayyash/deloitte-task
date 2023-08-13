package com.example.deloittetask.presentation.home.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deloittetask.BaseFragment
import com.example.deloittetask.R
import com.example.deloittetask.databinding.FragmentMoreBinding
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.showToast


class MoreFragment : BaseFragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MoreViewModel>()
    private var articleAdapter: ArticleAdapter? = null

    private val viewStateObserver: Observer<MoreViewModel.MoreViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        observeChanges()
    }

    private fun initRecyclerView() {
        binding.dashboardRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun initAdapter() {
        articleAdapter = ArticleAdapter()
    }

    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun handleViewState(state: MoreViewModel.MoreViewState?) {
        when (state) {
            is MoreViewModel.MoreViewState.Loading -> {
                handleLoadingState()
            }

            is MoreViewModel.MoreViewState.Success -> {
                handleSuccessState(state)
            }

            is MoreViewModel.MoreViewState.Failure -> {
                handleErrorState(state)
            }

            else -> {
                //Nothing to do here
            }
        }

    }

    private fun handleSuccessState(state: MoreViewModel.MoreViewState.Success) {
        binding.progressBar.isVisible = false
        articleAdapter?.submitList(state.articles)
    }


    private fun handleLoadingState() {
        binding.progressBar.isVisible = true
    }

    private fun handleErrorState(state: MoreViewModel.MoreViewState.Failure) {
        binding.progressBar.isVisible = false
        handleError(state)
    }

    private fun handleError(state: MoreViewModel.MoreViewState.Failure) {
        when (state.deloitteError) {
            is DeloitteError.GenericError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.error_no_internet_message))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.error_timeout_error_message))
            else -> {
                //Nothing to do here
            }
        }
    }


    companion object {
        fun newInstance() = MoreFragment()
    }
}
