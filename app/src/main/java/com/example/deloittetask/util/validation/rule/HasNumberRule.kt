package com.example.deloittetask.util.validation.rule

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.ErrorType
import com.example.deloittetask.util.validation.Rule
import java.util.regex.Pattern

private const val PATTERN = ".*\\d.*"

class HasNumberRule(
    context: Context,
    errorMessage: String = context.getString(R.string.error_validation_has_number)
) : Rule(Error(errorMessage, ErrorType.HAS_NUM)) {

    override fun isValid(text: String): Boolean {
        val pattern = Pattern.compile(PATTERN)
        return pattern.matcher(text).matches()
    }

}
