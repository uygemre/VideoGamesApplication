package com.base.core.datetimeutils


import java.text.SimpleDateFormat
import java.util.*

class CountTimer {

    fun getTimeAgo(postDate: String): String {

        return try {

            val inputFormatString = "yyyy-MM-dd'T'HH:mm:ss"
            val inputDateFormat = SimpleDateFormat(inputFormatString, Locale.getDefault())
            val inputDate: Date = inputDateFormat.parse(postDate)

            val date = Calendar.getInstance(Locale.getDefault()).time
            val diffs = date.time - inputDate.time
            val day = diffs / (24 * 60 * 60 * 1000)
            val hour = diffs / (60 * 60 * 1000)

            return when (day.toInt()) {
                0 -> """$hour saat önce"""
                in 1..6 -> """$day gün önce"""
                7 -> """1 hafta önce"""
                in 8..13 -> """$day gün önce"""
                14 -> """2 hafta önce"""
                in 15..20 -> """$day gün önce"""
                21 -> """3 hafta önce"""
                in 22..27 -> """$day hafta önce"""
                28 -> """4 hafta önce"""
                in 29..30 -> """$day hafta önce"""
                in 31..364 -> """${(day / 30)} ay önce"""
                else -> DateTimeUtils.formatWithStyle(
                    DateTimeUtils.formatDate(postDate),
                    DateTimeStyle.MEDIUM
                )
            }
        }catch (e: Exception){
            ""
        }
    }

    fun getToday(postDate: String?): String?{

        return try {
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val day = DateTimeUtils.getDateDiff(date, postDate, DateTimeUnits.DAYS)

            return when (day) {
                0 -> """Today"""
                else -> DateTimeUtils.formatWithStyle(
                    DateTimeUtils.formatDate(postDate),
                    DateTimeStyle.SHORT
                )
            }
        }catch (e: Exception) {
            ""
        }
    }
}