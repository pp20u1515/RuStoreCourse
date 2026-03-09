package com.example.rustorecourse.additiona_funcs

fun checkPhoneNumber(text: String): Boolean {
    if (text.isEmpty()) return false

    val firstChar = text.first()

    if (firstChar != '+' && !firstChar.isDigit()) return false
    if (text.count { it == '+' } > 1) return false
    if (text.contains('+') && firstChar != '+') return false
    if (text.startsWith("7") || text.startsWith("+7")) {
        if (text.startsWith("7") && !text.startsWith("+7")) {
            return false
        }
    }

    val digits = text.filter { it.isDigit() }

    if (digits.length != 11) return false

    val firstDigit = digits.first()

    return firstDigit == '7' || firstDigit == '8'
}