package com.example.deloittetask.presentation.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.deloittetask.BaseFragment
import com.example.deloittetask.R
import com.example.deloittetask.databinding.FragmentDashboardBinding
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.showToast

class DashboardFragment : BaseFragment() {


    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DashboardViewModel>()

    private val viewStateObserver: Observer<DashboardViewModel.DashboardViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChanges()
    }


    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun handleViewState(state: DashboardViewModel.DashboardViewState?) {
        when (state) {
            is DashboardViewModel.DashboardViewState.Loading -> {
                handleLoadingState()
            }

            is DashboardViewModel.DashboardViewState.Success -> {
                handleSuccessState(state)
            }

            is DashboardViewModel.DashboardViewState.Failure -> {
                handleErrorState(state)
            }

            else -> {
                //Nothing to do here
            }
        }

    }

    private fun handleSuccessState(state: DashboardViewModel.DashboardViewState.Success) {
        binding.progressBar.isVisible = false
        binding.textViewFullName.text = state.user.fullName
        binding.textViewEmail.text = state.user.email
        binding.textViewNationalIdValue.text = state.user.nationalId.toString()
        binding.textViewPhoneNumberValue.text = state.user.phoneNumber
        binding.textViewDobValue.text = state.user.dateOfBirth
    }

    private fun handleLoadingState() {
        binding.progressBar.isVisible = true
    }

    private fun handleErrorState(state: DashboardViewModel.DashboardViewState.Failure) {
        binding.progressBar.isVisible = false
        handleError(state)
    }

    private fun handleError(state: DashboardViewModel.DashboardViewState.Failure) {
        when (state.deloitteError) {
            is DeloitteError.GenericError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.no_internet_msg))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.timeout_error_msg))
        }
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}