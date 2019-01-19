package me.navid.scrambilo.ui.hints

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_word_outlined.*
import me.navid.scrambilo.R
import me.navid.scrambilo.extension.inflate
import me.navid.scrambilo.model.Term

class HintsAdapter : RecyclerView.Adapter<HintsAdapter.WordViewHolder>() {
    private val items = ArrayList<Term>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(parent.inflate(R.layout.item_word_outlined))

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun addAll(elements: Array<Term>) {
        items.addAll(elements)
        notifyDataSetChanged()
    }

    class WordViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Term) {
            text1.text = item.word
            text2.text = "${item.score}"
        }
    }
}
