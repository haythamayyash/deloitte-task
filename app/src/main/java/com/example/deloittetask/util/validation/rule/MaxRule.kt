package com.example.deloittetask.util.validation.rule

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.ErrorType
import com.example.deloittetask.util.validation.Rule

internal const val DEFAULT_MAX = 40

class MaxRule(
    context: Context,
    private val maximum: Int = DEFAULT_MAX,
    errorMessage: String = context.getString(R.string.error_validation_length_max, maximum)
) : Rule(Error(errorMessage, ErrorType.MAX)) {

    override fun isValid(text: String): Boolean {
        return text.length <= maximum
    }
}