package ru.skillbranch.dev_intensive.utils

import android.service.voice.AlwaysOnHotwordDetector

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName
            ?.split(" ")
            ?.filter { str -> str.filter { c -> c.isLetterOrDigit() }.isNotEmpty() }

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial = firstName?.firstOrNull { c -> c.isLetter() }?.toUpperCase()
        val lastInitial = lastName?.firstOrNull { c -> c.isLetter() }?.toUpperCase()

        firstInitial?.let { return "$firstInitial" + if (lastInitial != null) lastInitial else "" }
        lastInitial?.let { return "$lastInitial" }
        return null
    }

    private fun transliterateCharacter(c: Char): String {
        var res = when(c.toLowerCase()) {
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else -> "$c"
        }

        if(c.isUpperCase()) {
            res = "${res.firstOrNull()?.toUpperCase()}" + res.drop(1)
        }

        return res
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val result = StringBuilder()

        for (c in payload) {
            if (c == ' ') {
                result.append(divider)
            }
            else {
                result.append(Utils.transliterateCharacter(c))
            }
        }

        return result.toString()
    }
}