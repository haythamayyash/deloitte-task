package com.example.deloittetask.util.validation

data class Error(val message: String, val type: ErrorType)

enum class ErrorType {
    MIN,
    MAX,
    HAS_CAP,
    HAS_SMALL_LETTER,
    HAS_NUM,
    NO_SPACES,
    NO_DOUBLE_SPACES,
    HAS_SPEC_CHAR,
    LETTER_ONLY,
    REGEX
}