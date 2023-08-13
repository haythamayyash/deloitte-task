package com.example.deloittetask.util.validation.validator

import android.content.Context
import com.example.deloittetask.util.validation.rule.MaxRule
import com.example.deloittetask.util.validation.rule.MinRule
import com.example.deloittetask.util.validation.rule.RegularExpressionRule

internal const val VALIDATOR_NAME_MIN_LENGTH = 5
internal const val VALIDATOR_NAME_MAX_LENGTH = 30
internal const val NAME_REGEX = "[A-Z_0-9_a-z_٠-٩ـء-ي \\!_\\_\\-_\\,_\\'\\.]+"

class NameValidator(context: Context) : Validator() {
    init {
        addRule(MinRule(context, VALIDATOR_NAME_MIN_LENGTH))
        addRule(MaxRule(context, VALIDATOR_NAME_MAX_LENGTH))
        addRule(RegularExpressionRule(context, NAME_REGEX))
    }
}