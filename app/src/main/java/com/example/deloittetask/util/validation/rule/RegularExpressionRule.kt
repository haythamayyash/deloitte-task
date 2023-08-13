package com.example.deloittetask.util.validation.rule

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.ErrorType
import com.example.deloittetask.util.validation.Rule
import java.util.regex.Pattern

class RegularExpressionRule(
    context: Context,
    private val regex: String,
    errorMessage: String = context.getString(R.string.error_validation_default)
) : Rule(Error(errorMessage, ErrorType.REGEX)) {

    init {
        if (regex.isBlank()) {
            throw IllegalArgumentException("${this::class.java.simpleName} should have a valid non empty regular expression")
        }
    }

    override fun isValid(text: String): Boolean {
        val pattern = Pattern.compile(regex)
        return pattern.matcher(text).matches()
    }
}
