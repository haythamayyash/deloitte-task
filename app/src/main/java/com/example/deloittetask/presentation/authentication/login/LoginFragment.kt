package com.example.deloittetask.presentation.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.deloittetask.BaseFragment
import com.example.deloittetask.MainActivity
import com.example.deloittetask.R
import com.example.deloittetask.databinding.FragmentLoginBinding
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.debounceClick
import com.example.deloittetask.util.showToast
import com.example.deloittetask.util.validation.rule.MinRule
import com.example.deloittetask.util.validation.validator.PasswordValidator
import com.example.deloittetask.util.validation.validator.Validator
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment private constructor() : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var passwordValidator: Validator
    private val emailValidator by lazy {
        Validator().apply {
            addRule(MinRule(requireContext(), MIN_EMAIL_LOGIN))
        }
    }

    private val fieldsValidationResult = hashMapOf(
        R.id.text_input_layout_email to false, R.id.text_input_layout_password to false
    )

    private val viewStateObserver: Observer<LoginViewModel.LoginViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }

    private val eventObserver: Observer<LoginViewModel.LoginEvent?> by lazy {
        Observer { state ->
            handleEvents(state)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValidators()
        initListeners()
        initFocusChangeListeners()
        initTextChangeListeners()
        observeChanges()
    }

    private fun initValidators() {
        passwordValidator = PasswordValidator(requireContext())
    }

    private fun initListeners() {
        binding.buttonLogin.debounceClick {
            viewModel.login(
                binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
            )
        }
    }

    private fun initFocusChangeListeners() {
        binding.textInputLayoutEmail.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutEmail, emailValidator)
            }
        }
        binding.textInputLayoutPassword.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutPassword, passwordValidator)
            }
        }
    }

    private fun initTextChangeListeners() {
        binding.textInputLayoutEmail.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutEmail, emailValidator)
        }
        binding.textInputLayoutPassword.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutPassword, passwordValidator)
        }
    }

    private fun validateField(textInputLayout: TextInputLayout?, validator: Validator) {
        val result = when (val result = validator.validate(getTextFrom(textInputLayout))) {
            is Validator.Result.Success -> {
                setErrorMessageInto(textInputLayout)
                true
            }

            is Validator.Result.Failure -> {
                setErrorMessageInto(
                    textInputLayout, result.error.first().message
                )
                false
            }
        }
        updateValidationStatus(textInputLayout, result)
        updateContinueButtonStatus()
    }


    private fun setErrorMessageInto(textInputLayout: TextInputLayout?, errorMsg: String? = null) {
        textInputLayout?.error = errorMsg
    }

    private fun getTextFrom(textInputLayout: TextInputLayout?): String {
        return textInputLayout?.editText?.text?.toString()?.trim().toString()
    }

    private fun updateValidationStatus(
        textInputLayout: TextInputLayout?, validationResult: Boolean
    ) {
        textInputLayout ?: return
        fieldsValidationResult[textInputLayout.id] = validationResult
    }

    private fun updateContinueButtonStatus() {
        binding.buttonLogin.isEnabled = fieldsValidationResult.all { it.value }
    }


    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.events.observe(viewLifecycleOwner, eventObserver)
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

    private fun handleEvents(state: LoginViewModel.LoginEvent?) {
        when (state) {
            is LoginViewModel.LoginEvent.OpenHome -> {
                MainActivity.startActivity(requireContext())
                requireActivity().finish()
            }

            else -> {
                //Nothing to do here
            }
        }
    }

    private fun handleSuccessState() {
        binding.progressBar.isVisible = false
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
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.error_no_internet_message))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.error_timeout_error_message))
            is DeloitteError.LocalError -> handleLocalError(state.deloitteError)
        }
    }

    private fun handleLocalError(deloitteError: DeloitteError.LocalError) {
        if (deloitteError.errorCode == LoginViewModel.ERROR_CODE_WRONG_EMAIL_OR_PASSWORD) {
            showToast(getString(R.string.error_wrong_email_or_password))
        } else {
            showToast(getString(R.string.error_generic_message))
        }
    }

    companion object {
        const val MIN_EMAIL_LOGIN = 1
        fun newInstance() = LoginFragment()
    }
}