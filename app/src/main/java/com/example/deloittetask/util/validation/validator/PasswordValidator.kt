package com.example.deloittetask.util.validation.validator

import android.content.Context
import com.example.deloittetask.util.validation.rule.HasCapsRule
import com.example.deloittetask.util.validation.rule.HasNumberRule
import com.example.deloittetask.util.validation.rule.HasSmallLettersRule
import com.example.deloittetask.util.validation.rule.MaxRule
import com.example.deloittetask.util.validation.rule.MinRule
import com.example.deloittetask.util.validation.rule.NoSpacesRule

const val VALIDATOR_PASSWORD_MIN_LENGTH = 8
const val VALIDATOR_PASSWORD_MAX_LENGTH = 40

class PasswordValidator(context: Context) : Validator() {

    init {
        addRule(MinRule(context, VALIDATOR_PASSWORD_MIN_LENGTH))
        addRule(MaxRule(context, VALIDATOR_PASSWORD_MAX_LENGTH))
        addRule(HasNumberRule(context))
        addRule(NoSpacesRule(context))
        addRule(HasCapsRule(context))
        addRule(HasSmallLettersRule(context))
    }
}