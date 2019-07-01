package ru.skillbranch.dev_intensive.extensions

import java.util.*
import kotlin.math.abs

val SECOND: Long = 1000
val MINUTE: Long = 60000
val HOUR: Long = 3600000
val DAY: Long = 86400000

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY
}

fun Date.add(value: Long, timeUnit: TimeUnits): Date {
    var multiplier = when(timeUnit) {
        TimeUnits.DAY -> DAY
        TimeUnits.HOUR -> HOUR
        TimeUnits.MINUTE -> MINUTE
        TimeUnits.SECOND -> SECOND
    }

    return Date(time + multiplier * value)
}

fun Date.format(format: String = "HH:mm:ss dd.MM.yy"): String {
    fun numberFormat(number: Int, digits: Int): String {
        val str = "$number"

        if (str.length > digits) {
            return str.drop(str.length - digits)
        }
        else {
            val result = StringBuilder()

            for (i in str.length.until(digits)) {
                result.append('0')
            }

            result.append(number)
            return result.toString()
        }
    }

    val result = StringBuilder()
    val timeChars = arrayListOf<Char>('H', 'h', 'd', 'D', 'y', 'Y', 'm', 'M', 's', 'S')
    var currentChar = '?'
    var currentLength = 0

    for (c in format) {
        if (c != currentChar) {
            when(currentChar) {
                'h', 'H' -> result.append(numberFormat(hours, currentLength))
                'd', 'D' -> result.append(numberFormat(date, currentLength))
                's', 'S' -> result.append(numberFormat(seconds, currentLength))
                'm' -> result.append(numberFormat(minutes, currentLength))
                'M' -> result.append(numberFormat(month, currentLength))
                'y', 'Y' -> result.append(numberFormat(year, currentLength))
            }

            currentLength = 0
            currentChar = c
        }

        currentLength++

        if (c !in timeChars) {
            result.append(c)
        }
    }

    when(currentChar) {
        'h', 'H' -> result.append(numberFormat(hours, currentLength))
        'd', 'D' -> result.append(numberFormat(date, currentLength))
        's', 'S' -> result.append(numberFormat(seconds, currentLength))
        'm' -> result.append(numberFormat(minutes, currentLength))
        'M' -> result.append(numberFormat(month, currentLength))
        'y', 'Y' -> result.append(numberFormat(year, currentLength))
    }

    return result.toString()
}

fun Date.humanizeDiff(date: Date = Date()): String {
    fun approximation(number: Long, divider: Long): Long {
        return number / divider + if (number % divider <= divider / 2) 0 else 1
    }

    val trueDiff = abs(this.time - date.time)

    var diff = approximation(trueDiff, SECOND)



    if (diff <= 1) {
        return "только что"
    }

    if (diff <= 45) {
        return "несколько секунд назад"
    }

    if (diff <= 75) {
        return "минуту назад"
    }

    diff = approximation(trueDiff, MINUTE)

    if (diff <= 45) {
        val noun = if (diff % 100 in 11..19) "минут" else when (diff % 10) {
            1L -> "минуту"
            2L, 3L, 4L -> "минуты"
            else -> "минут"
        }

        return "$diff $noun назад"
    }

    if (diff <= 75) {
        return "час назад"
    }

    diff = approximation(trueDiff, HOUR)

    if (diff <= 22) {
        val noun = if (diff % 100 in 11..19) "часов" else when (diff % 10) {
            1L -> "час"
            2L, 3L, 4L -> "часа"
            else -> "часов"
        }

        return "$diff $noun назад"
    }

    if (diff <= 26) {
        return "день назад"
    }

    diff = approximation(trueDiff, DAY)

    if (diff <= 360) {
        val noun = if (diff % 100 in 11..19) "дней" else when (diff % 10) {
            1L -> "день"
            2L, 3L, 4L -> "дня"
            else -> "дней"
        }

        return "$diff $noun назад"
    }

    return "более года назад"
}