package me.navid.scrambilo.ui.game.board

import android.view.MotionEvent

/**
 * Class for intercepting user's touches
 */
class TouchProcessor(private val listener: OnTouchProcessed, threshold: Float) {
    private var isDown: Boolean = false
    private var moveThreshold: Float = Math.max(threshold, 0.1f)
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f

    fun onTouchEvent(event: MotionEvent, position: Int): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                isDown = true
                listener.onDown(event, position)
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                isDown = false
                listener.onUp(event, position)
            }
            MotionEvent.ACTION_MOVE ->
                if (isDown && (Math.abs(lastX - event.x) > moveThreshold || Math.abs(lastY - event.y) > moveThreshold)) {
                    lastX = event.x
                    lastY = event.y
                    listener.onMove(event, position)
                }
            else -> return false
        }
        return true
    }

    interface OnTouchProcessed {
        fun onDown(event: MotionEvent, position: Int)
        fun onMove(event: MotionEvent, position: Int)
        fun onUp(event: MotionEvent, position: Int)
    }
}