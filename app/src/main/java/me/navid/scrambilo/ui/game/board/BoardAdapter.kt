package me.navid.scrambilo.ui.game.board

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_letter.*
import me.navid.scrambilo.R
import me.navid.scrambilo.extension.inflate
import me.navid.scrambilo.model.Alphabet
import me.navid.scrambilo.util.ColorUtils

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.LetterViewHolder>() {
    private var items = ArrayList<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        return LetterViewHolder(parent.inflate(R.layout.item_letter))
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(pos: Int, item: AdapterItem) {
        items.add(pos, item)
        notifyItemInserted(pos)
    }

    fun addAll(elements: Array<AdapterItem>) {
        items.addAll(elements)
        notifyDataSetChanged()
    }

    fun clear() {
        items = ArrayList()
        notifyDataSetChanged()
    }

    fun selectItem(position: Int, color: Int) {
        if (!items[position].selected) {
            items[position].selected = true
            items[position].color = color
            notifyItemChanged(position)
        }
    }

    fun deselectItems(positions: IntArray) {
        positions.forEach {
            items[it].selected = false
            items[it].color = ColorUtils.defaultColor
        }
        notifyDataSetChanged()
    }

    operator fun get(position: Int) = items[position]

    class LetterViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: AdapterItem) {
            letterText.text = item.alphabet.character.toString()
            cardView.setCardBackgroundColor(if (item.selected) item.color else ColorUtils.defaultColor)
        }
    }

    data class AdapterItem(
        val alphabet: Alphabet,
        var selected: Boolean = false,
        @ColorInt var color: Int = ColorUtils.defaultColor
    )
}