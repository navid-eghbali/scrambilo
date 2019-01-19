package me.navid.scrambilo.ui.game.board

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.navid.scrambilo.util.ColorUtils

/**
 * Holder of a RecyclerView and it's adapter
 */
class BoardView(rv: RecyclerView, private val listener: OnLetterSelectionListener) {
    private val boardAdapter = BoardAdapter()
    private val itemTouchListener = MyItemTouchListener(ItemTouchInteraction())

    init {
        rv.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = boardAdapter
            addOnItemTouchListener(itemTouchListener)
        }
    }

    fun setAdapter(data: Array<BoardAdapter.AdapterItem>) {
        boardAdapter.addAll(data)
    }

    fun popWord() {
        val poppedWord = itemTouchListener.popWord()
        poppedWord?.let { boardAdapter.deselectItems(IntArray(it.letters.size) { index -> it.letters[index] }) }
    }

    fun getText(word: MyItemTouchListener.Word) =
        StringBuilder().apply { word.letters.forEach { index -> append(boardAdapter[index].alphabet.character) } }.toString()

    fun getScore(word: MyItemTouchListener.Word) =
        word.letters.sumBy { index -> boardAdapter[index].alphabet.score }

    private inner class ItemTouchInteraction : MyItemTouchListener.OnInteractionListener {
        @ColorInt
        private var color = Color.WHITE

        override fun onTouchBegin(position: Int?, word: MyItemTouchListener.Word) {
            color = ColorUtils.randomColor()
            position?.let { boardAdapter.selectItem(it, color) }
            listener.onSelectionBegin(getText(word))
        }

        override fun onTouchMove(position: Int?, word: MyItemTouchListener.Word) {
            position?.let {
                boardAdapter.selectItem(it, color)
                listener.onSelectionMove(getText(word))
            }
        }

        override fun onTouchEnd(position: Int?, word: MyItemTouchListener.Word) {
            position?.let { boardAdapter.selectItem(it, color) }
            listener.onSelectionEnd(getText(word), getScore(word))
        }

        override fun onTouchCanceled() {
            popWord()
        }
    }

    interface OnLetterSelectionListener {
        fun onSelectionBegin(str: String)
        fun onSelectionMove(str: String)
        fun onSelectionEnd(str: String, score: Int)
    }
}