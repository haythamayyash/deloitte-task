package com.example.deloittetask.util.validation.validator

import android.content.Context
import com.example.deloittetask.R
import com.example.deloittetask.util.validation.rule.RegularExpressionRule

private const val EMAIL_REGEX =
    "^[_a-zA-Z0-9-]+((\\.[_a-zA-Z0-9-]+)*|(\\+[_a-zA-Z0-9-]+)*)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})\$"

class EmailValidator(context: Context) : Validator() {
    init {
        addRule(
            RegularExpressionRule(
                context,
                EMAIL_REGEX,
                context.getString(R.string.error_validation_invalid_email)
            )
        )
    }
}