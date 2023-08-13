package com.example.deloittetask.util.validation.rule

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.ErrorType
import com.example.deloittetask.util.validation.Rule

private const val PATTERN = " "

class NoSpacesRule(
    context: Context,
    errorMessage: String = context.getString(R.string.error_validation_space)
) : Rule(Error(errorMessage, ErrorType.NO_SPACES)) {

    override fun isValid(text: String): Boolean {
        return !text.contains(PATTERN)
    }
}