package io.github.arshcaria.kerberos

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.arshcaria.kerberos.model.Item
import org.jetbrains.anko.layoutInflater

class RvAdapter : RecyclerView.Adapter<RvAdapter.Holder>() {

    private var items: List<Item>? = null

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var title = "INVALID NOTE"
        var content = ""

        items?.let { items ->
            val item = items[position]
            title = item.title
            content = item.content
        }

        holder.tvTitle.text = title
        holder.tvContent.text = content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent.context.layoutInflater.inflate(R.layout.listitem_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun setData(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.listitem_item_title)
        val tvContent: TextView = itemView.findViewById(R.id.listitem_item_content)
    }
}
