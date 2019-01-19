package me.navid.scrambilo.ui.game.board

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * My custom ItemTouchListener that saves the last position of clicked cell and also after ending pops the user's word.
 * It holds the user's words in an stack.
 */
class MyItemTouchListener(private val listener: OnInteractionListener) : RecyclerView.OnItemTouchListener {
    private val words = Stack<Word>()
    private val touchProcessor = TouchProcessor(TouchProcessedListener(), 3.0f)

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null) {
            val clickedPosition = rv.getChildLayoutPosition(child)
            touchProcessor.onTouchEvent(e, clickedPosition)
        } else
            listener.onTouchCanceled()
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    fun popWord() = if (!words.empty()) words.pop() else null

    private inner class TouchProcessedListener : TouchProcessor.OnTouchProcessed {
        private var lastPosition: Int? = null

        override fun onDown(event: MotionEvent, position: Int) {
            val word = Word(arrayListOf(position))
            words.push(word)
            lastPosition = position

            listener.onTouchBegin(position, word)
        }

        override fun onMove(event: MotionEvent, position: Int) {
            if (words.isEmpty() || lastPosition == null) return

            val word = words.peek()
            var letterIndex: Int? = null
            if (position != lastPosition && inRange(position, lastPosition!!)) {
                letterIndex = position
                word.letters.add(letterIndex)
                lastPosition = position
            }

            listener.onTouchMove(letterIndex, word)
        }

        override fun onUp(event: MotionEvent, position: Int) {
            if (words.isEmpty()) return

            val word = words.peek()
            var lastIndex: Int? = null
            if (position != lastPosition) {
                lastIndex = position
                word.letters.add(lastIndex)
            }
            lastPosition = null

            listener.onTouchEnd(lastIndex, word)
        }

        private fun inRange(newPos: Int, lastPos: Int) =
            (newPos == lastPos - 1) || (newPos == lastPos + 1) || (newPos == lastPos - 5) || (newPos == lastPos + 5)
    }

    interface OnInteractionListener {
        fun onTouchBegin(position: Int?, word: Word)
        fun onTouchMove(position: Int?, word: Word)
        fun onTouchEnd(position: Int?, word: Word)
        fun onTouchCanceled()
    }

    data class Word(val letters: ArrayList<Int>)
}