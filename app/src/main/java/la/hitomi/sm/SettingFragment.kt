package la.hitomi.sm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_setting.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class SettingFragment : Fragment() {

    private val mItems = ArrayList<ChatItem>()
    private var adapter: ChatAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)


        initRecyclerView(view)
        addDummy()
        return view
    }

    private fun initRecyclerView(view: View) { // RecyclerView 기본세팅
        // 변경될 가능성 o : false 로 , 없다면 true.

        adapter = ChatAdapter(mItems)
        view.recyclerViews.adapter = adapter
        view.recyclerViews.layoutManager = LinearLayoutManager(activity)
    }

    private fun addDummy(){
        mItems.add(ChatItem("with 방진혁","국민대 캠프"))
        mItems.add(ChatItem("with 김태양","발기부전 치료"))
    }

}