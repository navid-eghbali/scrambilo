package me.navid.scrambilo.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_simple.*
import me.navid.scrambilo.R
import me.navid.scrambilo.extension.inflate

class HomeAdapter(private val clickListener: (Int?) -> Unit) : RecyclerView.Adapter<HomeAdapter.SimpleViewHolder>() {
    private val items = ArrayList<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SimpleViewHolder(parent.inflate(R.layout.item_simple), clickListener)

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun addAll(elements: Array<AdapterItem>) {
        items.addAll(elements)
        notifyDataSetChanged()
    }

    class SimpleViewHolder(override val containerView: View, private val itemClickListener: (Int?) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: AdapterItem) {
            text1.text = item.title
            itemView.setOnClickListener { itemClickListener(item.actionId) }
        }
    }

    data class AdapterItem(val title: String, @IdRes val actionId: Int?)
}