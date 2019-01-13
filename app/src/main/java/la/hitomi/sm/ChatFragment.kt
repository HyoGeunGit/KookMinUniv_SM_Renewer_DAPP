package la.hitomi.sm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_chat.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class ChatFragment : Fragment() {

    private val mItems = ArrayList<ChatItem>()
    private var adapter: ChatAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)


        initRecyclerView(view)
        view.send.onClick {
            mItems.add(ChatItem("심효근", view.form.text.toString()))
            view.form.setText("")
            adapter!!.notifyDataSetChanged()
        }
        addDummy()
    return view
    }

    private fun initRecyclerView(view: View) { // RecyclerView 기본세팅
        // 변경될 가능성 o : false 로 , 없다면 true.
        view.recyclerView.setHasFixedSize(false)

        adapter = ChatAdapter(mItems)
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun addDummy(){
        mItems.add(ChatItem("방진혁","국민머캠프 늦으면 우영이누나한테 ㅗ된다"))
        mItems.add(ChatItem("심효근","ㅗ됬네"))
        mItems.add(ChatItem("심효근","중간에 나오는거 ㅆㄱㄴ?"))
        mItems.add(ChatItem("방진혁","학교 ㅆㅂ"))
        adapter!!.notifyDataSetChanged()
    }

}