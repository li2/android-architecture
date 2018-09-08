package me.li2.android.architecture.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN_yyyy_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss"
const val DATE_PATTERN_dd_MM_yyyy = "dd/MM/yyyy"

fun getLocalDateFromString(timestamp: String?, pattern: String): Date? =
        if (timestamp != null) getDateFromStringWithPattern(timestamp, pattern, TimeZone.getDefault()) else null

internal fun getDateFromStringWithPattern(timestamp: String, pattern: String, timeZone: TimeZone?): Date? =
        try {
            val sdf = SimpleDateFormat(pattern)
            timeZone?.let {
                sdf.timeZone = timeZone
            }
            sdf.parse(timestamp)
        } catch (e: Exception) {
            println("Error getDateFromStringWithPattern($timestamp, $pattern): $e.message")
            null
        }
