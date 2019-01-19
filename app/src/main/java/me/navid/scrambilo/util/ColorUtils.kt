package me.navid.scrambilo.util

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.random.Random
import kotlin.random.nextInt

object ColorUtils {
    @ColorInt
    val defaultColor = Color.parseColor("#ff373740")

    @ColorInt
    fun randomColor(): Int {
        val range = 64 until 192
        return Color.rgb(Random.nextInt(range), Random.nextInt(range), Random.nextInt(range))
    }
}