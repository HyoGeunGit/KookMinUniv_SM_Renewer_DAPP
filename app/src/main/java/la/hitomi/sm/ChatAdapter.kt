package la.hitomi.sm

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_chat.view.*
import org.jetbrains.anko.textColor
import java.util.ArrayList

/**
 * Created by hyunjin on 2018. 5. 11..
 */
class ChatAdapter(private var mItems: ArrayList<ChatItem>) : RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {

    var id: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name.text = mItems[position].name
        if(mItems[position].name=="심효근") {
            holder.name.textColor = Color.RED
        }

        if(mItems[position].name=="우현진") {
            holder.name.textColor = Color.BLACK
        }
        holder.content.text = mItems[position].content
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    // binding widgets on item layout
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.name
        val content: TextView = itemView.content
    }
}

data class ChatItem(val name: String, val content: String)

