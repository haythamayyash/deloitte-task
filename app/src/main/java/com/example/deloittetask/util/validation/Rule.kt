package com.example.deloittetask.util.validation


abstract class Rule(val error: Error) {
    abstract fun isValid(text: String): Boolean
}






