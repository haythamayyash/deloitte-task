package com.example.deloittetask.presentation.authentication.registration

import android.app.DatePickerDialog
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
import com.example.deloittetask.databinding.FragmentRegistrationBinding
import com.example.deloittetask.domain.model.User
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.debounceClick
import com.example.deloittetask.util.showToast
import com.example.deloittetask.util.validation.rule.MinRule
import com.example.deloittetask.util.validation.rule.NoSpacesRule
import com.example.deloittetask.util.validation.validator.EmailValidator
import com.example.deloittetask.util.validation.validator.NameValidator
import com.example.deloittetask.util.validation.validator.PasswordValidator
import com.example.deloittetask.util.validation.validator.PhoneNumberValidator
import com.example.deloittetask.util.validation.validator.Validator
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class RegistrationFragment private constructor() : BaseFragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegistrationViewModel>()

    private lateinit var emailValidator: Validator
    private lateinit var nameValidator: Validator
    private lateinit var passwordValidator: Validator
    private lateinit var phoneNumberValidator: Validator

    private val fieldsValidationResult = hashMapOf(
        R.id.text_input_layout_email to false,
        R.id.text_input_layout_national_id to false,
        R.id.text_input_layout_phone_number to false,
        R.id.text_input_layout_dob to false,
        R.id.text_input_layout_password to false
    )
    private val viewStateObserver: Observer<RegistrationViewModel.RegistrationViewState?> by lazy {
        Observer { state ->
            handleViewState(state)
        }
    }
    private val eventObserver: Observer<RegistrationViewModel.RegistrationEvent?> by lazy {
        Observer { state ->
            handleEvents(state)
        }
    }


    private val nationalIdValidator by lazy {
        Validator().apply {
            addRule(NoSpacesRule(requireContext()))
            addRule(MinRule(requireContext(), MIN_NATIONAL_ID_NUMBER))
        }

    }
    private val dobValidator by lazy {
        Validator().apply {
            addRule(NoSpacesRule(requireContext()))
            addRule(MinRule(requireContext(), MIN_DOB_NUMBER))
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
        initValidator()
        initListener()
        initFocusChangeListeners()
        initTextChangeListeners()
        observeChanges()
    }

    private fun initValidator() {
        emailValidator = EmailValidator(requireContext())
        nameValidator = NameValidator(requireContext())
        passwordValidator = PasswordValidator(requireContext())
        phoneNumberValidator = PhoneNumberValidator(requireContext())
    }

    private fun initListener() {
        binding.buttonRegister.debounceClick {
            viewModel.register(getUser())
        }
        binding.editTextDob.debounceClick {
            showDatePickerDialog()
        }
    }

    private fun initTextChangeListeners() {
        binding.textInputLayoutFullName.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutFullName, nameValidator)
        }
        binding.textInputLayoutEmail.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutEmail, emailValidator)
        }
        binding.textInputLayoutNationalId.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutNationalId, nationalIdValidator)
        }
        binding.textInputLayoutPhoneNumber.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutPhoneNumber, phoneNumberValidator)
        }
        binding.textInputLayoutDob.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutDob, dobValidator)
        }
        binding.textInputLayoutPassword.editText?.doAfterTextChanged {
            validateField(binding.textInputLayoutPassword, passwordValidator)
        }
    }

    private fun observeChanges() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.events.observe(viewLifecycleOwner, eventObserver)
    }

    private fun initFocusChangeListeners() {
        binding.textInputLayoutFullName.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutFullName, nameValidator)
            }
        }

        binding.textInputLayoutEmail.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutEmail, emailValidator)
            }
        }

        binding.textInputLayoutNationalId.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutNationalId, nationalIdValidator)
            }
        }

        binding.textInputLayoutPhoneNumber.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutPhoneNumber, phoneNumberValidator)
            }
        }
        binding.textInputLayoutDob.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutDob, dobValidator)
            }
        }

        binding.textInputLayoutPassword.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateField(binding.textInputLayoutPassword, passwordValidator)
            }
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
                    textInputLayout,
                    result.error.first().message
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
        textInputLayout: TextInputLayout?,
        validationResult: Boolean
    ) {
        textInputLayout ?: return
        fieldsValidationResult[textInputLayout.id] = validationResult
    }

    private fun updateContinueButtonStatus() {
        binding.buttonRegister.isEnabled = fieldsValidationResult.all { it.value }
    }

    private fun getUser(): User {
        return User(
            binding.editTextEmail.text.toString(),
            binding.editTextFullName.text.toString(),
            binding.editTextNationalId.text.toString().toLongOrNull() ?: 0,
            binding.editTextPhoneNumber.text.toString(),
            binding.editTextDob.text.toString(),
            binding.editTextPassword.text.toString(),

            )
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

    private fun handleEvents(state: RegistrationViewModel.RegistrationEvent?) {
        when (state) {
            is RegistrationViewModel.RegistrationEvent.OpenHome -> {
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

    private fun handleErrorState(state: RegistrationViewModel.RegistrationViewState.Failure) {
        binding.progressBar.isVisible = false
        handleError(state)
    }

    private fun handleError(state: RegistrationViewModel.RegistrationViewState.Failure) {
        when (state.deloitteError) {
            is DeloitteError.GenericError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.NoInternetConnection -> showToast(getString(R.string.error_no_internet_message))
            is DeloitteError.ServerError -> showToast(state.deloitteError.errorMessage)
            is DeloitteError.TimeOutConnection -> showToast(getString(R.string.error_timeout_error_message))
            is DeloitteError.LocalError -> handleLocalError(state.deloitteError)
        }
    }

    private fun handleLocalError(deloitteError: DeloitteError.LocalError) {
        if (deloitteError.errorCode == RegistrationViewModel.ERROR_CODE_ALREADY_EXIST_USER) {
            showToast(getString(R.string.error_user_already_exist))
        } else {
            showToast(getString(R.string.error_generic_message))
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)

            binding.editTextDob.setText(formattedDate)
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), dateListener, year, month, day)
        datePickerDialog.show()
    }

    companion object {
        const val MIN_NATIONAL_ID_NUMBER = 10
        const val MIN_DOB_NUMBER = 5
        const val DATE_FORMAT = "yyyy-MM-dd"
        fun newInstance() = RegistrationFragment()
    }
}