package com.example.whosthatpokemon.utils

fun toRoman(num: Int): String {
    if (num < 1 || num > 3999) {
        return "Invalid input: Number must be between 1 and 3999"
    }

    val romanNumerals = listOf(
        1000 to "M", 900 to "CM", 500 to "D", 400 to "CD", 100 to "C",
        90 to "XC", 50 to "L", 40 to "XL", 10 to "X",
        9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
    )

    var number = num
    val roman = StringBuilder()

    for ((value, symbol) in romanNumerals) {
        while (number >= value) {
            roman.append(symbol)
            number -= value
        }
    }

    return roman.toString()
}