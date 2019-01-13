package la.hitomi.sm

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_people.view.*
import kotlinx.android.synthetic.main.item_prelist.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleFragment : Fragment() {
    private val items = java.util.ArrayList<Data>()
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_people, container, false)

        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView!!.adapter = RecyclerAdapter(items)
        adapter = recyclerView!!.adapter as RecyclerAdapter?

        items += Data("중요한 일정", "선린인터넷 고등학교 EDCAN실", "20시간 5분 전")
        items += Data("늦지 말기", "국민머학교 캠프", "1일 전")
        recyclerView!!.adapter.notifyDataSetChanged()

        Client.retrofitService.getsnsList().enqueue(object : Callback<ArrayList<PeopleRepo>> {
            override fun onResponse(call: Call<ArrayList<PeopleRepo>>?, response: Response<ArrayList<PeopleRepo>>?) {
                val repo = response!!.body()

                when (response.code()) {
                    200 -> {
                        repo!!.indices.forEach {
                            items += Data(repo[it].id!!, repo[it].writer!!, repo[it].date!!)
                            recyclerView!!.adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<PeopleRepo>>?, t: Throwable?) {
                Log.v("C2cTest", "fail!!")
            }
        })
        return view
    }
    inner class RecyclerAdapter(private val dataList: ArrayList<Data>) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

        override fun onBindViewHolder(holder: Holder, position: Int) {

            with(holder.itemView) {
                val data = dataList[position]

                title.text = data.id
               location.text = data.writer
                remainTime.text = data.content

            }
        }
        override fun getItemCount(): Int = dataList.size

        inner class Holder(parent: ViewGroup)
            : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prelist, parent, false))
    }
}