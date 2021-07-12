package com.lollipop.e_lapor.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateFormatLocale {
    private val locale = Locale("in", "ID")

    private val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", locale)
    private val dateOnly = SimpleDateFormat("yyyy-MM-dd", locale)
    private val dateWithoutSpace = SimpleDateFormat("yyyyMMdd", locale)

    private val localeDateTime = SimpleDateFormat("EEEE, d MMMM y", locale)


    /**
     *  Create locale ID datetime format with full month without space
     *
     *  example input : 2017-11-29
     *
     *  example output : 20170911
     *
     *  @param time as Timestamp
     */

    fun localeDateParseWithoutSpace(time: String): String {
        val date = dateOnly.parse(time)
        return dateWithoutSpace.format(date)
    }


    /**
     * Get this date time with eg format 2020-12-12 12:12:12
     *
     *  Example output : 2017-11-09 00:00:00
     */
    fun getDateTimeNow(): String {
        timestamp.timeZone = TimeZone.getTimeZone("GMT+7")
        return timestamp.format(Date())
    }
}