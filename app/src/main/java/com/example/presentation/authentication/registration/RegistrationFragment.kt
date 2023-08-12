package com.example.presentation.authentication.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.BaseFragment
import com.example.deloittetask.R
import com.example.deloittetask.databinding.FragmentRegistrationBinding
import com.example.domain.model.User
import com.example.presentation.authentication.login.LoginViewModel
import com.example.util.DeloitteError
import com.example.util.debounceClick
import com.example.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment private constructor() : BaseFragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegistrationViewModel>()

    private val viewStateObserver: Observer<RegistrationViewModel.RegistrationViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        observeChanges()
    }

    private fun initListener() {
        binding.buttonRegister.debounceClick {
            viewModel.register(getUser())
        }
    }

    private fun getUser(): User {
        return User(
            binding.editTextEmail.text.toString(),
            binding.editTextFullName.text.toString(),
            binding.editTextNationalId.text.toString().toIntOrNull() ?: 0,
            binding.editTextPhoneNumber.text.toString(),
            binding.editTextDob.text.toString(),
            binding.editTextPassword.text.toString(),

            )
    }

    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun handleViewState(state: RegistrationViewModel.RegistrationViewState?) {
        when (state) {
            is RegistrationViewModel.RegistrationViewState.Loading -> {
                handleLoadingState()
            }

            is RegistrationViewModel.RegistrationViewState.Success -> {
                handleSuccessState()
            }

            is RegistrationViewModel.RegistrationViewState.Failure -> {
                handleErrorState(state)
            }

            else -> {
                //Nothing to do here
            }
        }

    }

    private fun handleSuccessState() {
        binding.progressBar.isVisible = false
        showToast("TODO GO TO NEXT SCREEN")
    }

    private fun handleLoadingState() {
        binding.progressBar.isVisible = true
    }

    private fun handleErrorState(state: RegistrationViewModel.RegistrationViewState.Failure) {
        binding.progressBar.isVisible = false
        handleError(state)
    }

    private fun handleError(state: RegistrationViewModel.RegistrationViewState.Failure) {
        when (state.deloitteError) {
            is DeloitteError.GenericError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.no_internet_msg))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.timeout_error_msg))
        }
    }

    companion object {
        fun newInstance() = RegistrationFragment()
    }
}