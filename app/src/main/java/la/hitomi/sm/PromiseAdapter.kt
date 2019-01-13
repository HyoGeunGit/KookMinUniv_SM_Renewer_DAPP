package la.hitomi.sm

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_prelist.view.*
import java.util.ArrayList

/**
 * Created by hyunjin on 2018. 5. 11..
 */
class PromiseAdapter(private var mItems: ArrayList<PromiseItem>, val listener: OnItemClickListener) : RecyclerView.Adapter<PromiseAdapter.ItemViewHolder>() {

    var id: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prelist, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        holder.location.text = mItems[position].location
        holder.remain.text = mItems[position].remain

        holder.itemView.setOnClickListener { listener.onClick(position) }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    // binding widgets on item layout
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.title
        val location: TextView = itemView.location
        val remain: TextView = itemView.remainTime
    }
}

data class PromiseItem(val title: String, val location: String, val remain: String)