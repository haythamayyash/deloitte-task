package com.example.deloittetask.util.validation.rule

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.ErrorType
import com.example.deloittetask.util.validation.Rule

internal const val DEFAULT_MIN = 5

class MinRule(
    context: Context,
    private val minimum: Int = DEFAULT_MIN,
    errorMessage: String = context.getString(R.string.error_validation_length_min, minimum)
) : Rule(Error(errorMessage, ErrorType.MIN)) {

    override fun isValid(text: String): Boolean {
        return text.length >= minimum
    }
}