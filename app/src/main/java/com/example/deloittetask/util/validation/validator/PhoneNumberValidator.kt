package com.example.deloittetask.util.validation.validator

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.rule.RegularExpressionRule

const val PHONE_NUMBER_REGEX = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"

class PhoneNumberValidator(context: Context) : Validator() {
    init {
        addRule(RegularExpressionRule(context, PHONE_NUMBER_REGEX, context.getString(R.string.error_validation_invalid_phone_number)))
    }
}