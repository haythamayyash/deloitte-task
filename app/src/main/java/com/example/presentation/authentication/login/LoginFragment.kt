package com.example.presentation.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.BaseFragment
import com.example.deloittetask.R
import com.example.deloittetask.databinding.FragmentLoginBinding
import com.example.util.DeloitteError
import com.example.util.debounceClick
import com.example.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment private constructor() : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    private val viewStateObserver: Observer<LoginViewModel.LoginViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeChanges()
    }

    private fun initListeners() {
        binding.buttonLogin.debounceClick {
            viewModel.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
    }

    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun handleViewState(state: LoginViewModel.LoginViewState?) {
        when (state) {
            is LoginViewModel.LoginViewState.Loading -> {
                handleLoadingState()
            }

            is LoginViewModel.LoginViewState.Success -> {
                handleSuccessState()
            }

            is LoginViewModel.LoginViewState.Failure -> {
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

    private fun handleErrorState(state: LoginViewModel.LoginViewState.Failure) {
        binding.progressBar.isVisible = false
        handleError(state)
    }

    private fun handleError(state: LoginViewModel.LoginViewState.Failure) {
        when (state.deloitteError) {
            is DeloitteError.GenericError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.no_internet_msg))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.timeout_error_msg))
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}