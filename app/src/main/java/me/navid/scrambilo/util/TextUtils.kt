package me.navid.scrambilo.util

object TextUtils {
    fun getTimeFormatted(time: Int) = "${pad(time / 60)}:${pad(time % 60)}"

    fun pad(number: Int) = if (number > 9) number.toString() else "0$number"
}