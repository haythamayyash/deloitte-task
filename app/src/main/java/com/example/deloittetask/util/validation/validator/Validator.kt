package com.example.deloittetask.util.validation.validator

import com.example.deloittetask.util.validation.Error
import com.example.deloittetask.util.validation.Rule


open class Validator {

    sealed class Result {
        object Success : Result()
        data class Failure(val error: List<Error>) : Result()
    }

    private var rules: MutableList<Rule> = mutableListOf<Rule>()
    private var errors: MutableList<Error> = mutableListOf<Error>()

    fun addRule(rule: Rule): Validator {
        rules.add(rule)
        return this
    }


    fun validate(text: String): Result {
        if (rules.isEmpty()) {
            return Result.Success
        }
        errors.clear()

        for (rule: Rule in rules) {
            if (!rule.isValid(text)) {
                errors.add(rule.error)
            }
        }

        return if (errors.isEmpty()) Result.Success else Result.Failure(errors)
    }
}