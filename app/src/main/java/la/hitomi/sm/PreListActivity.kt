package la.hitomi.sm

import android.content.ContentUris
import android.provider.CalendarContract
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_prelist.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class PreListActivity : BaseActivity(), OnItemClickListener {
    override fun onClick(position: Int) {
        startActivity<DetailProActivity>("time" to mItems[position].remain, "title" to mItems[position].title)
    }

    override var viewId: Int = R.layout.activity_prelist
    override var toolbarId: Int? = R.id.toolbar

    private val mItems = ArrayList<PromiseItem>()
    private var adapter: PromiseAdapter? = null

    var year = 2000
    var month = 1
    var day = 1

    override fun onCreate() {
        year = intent.getIntExtra("year", 2000)
        month = intent.getIntExtra("month", 1) + 1
        day = intent.getIntExtra("day", 1)
        toast(""+year+""+month+""+day)
        initRecyclerView()
        readEvents()
    }

    private fun initRecyclerView() { // RecyclerView 기본세팅
        // 변경될 가능성 o : false 로 , 없다면 true.
        recyclerView.setHasFixedSize(false)

        adapter = PromiseAdapter(mItems, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun readEvents() {
        val INSTANCE_PROJECTION = arrayOf(CalendarContract.Instances.EVENT_ID, // 0
                CalendarContract.Instances.BEGIN, // 1
                CalendarContract.Instances.TITLE, // 2
                CalendarContract.Instances.ORGANIZER)

        // The indices for the projection array above.
        val PROJECTION_ID_INDEX = 0
        val PROJECTION_BEGIN_INDEX = 1
        val PROJECTION_TITLE_INDEX = 2
        val PROJECTION_ORGANIZER_INDEX = 3

        // Specify the date range you want to search for recurring event instances
        val beginTime = Calendar.getInstance()
        //beginTime.set(2017, 9, 23, 8, 0)
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime.set(2018, 12, 30, 8, 0)
        val endMillis = endTime.timeInMillis


        // Construct the query with the desired date range.
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)

        // Submit the query
        val cur = contentResolver.query(builder.build(), INSTANCE_PROJECTION, null, null, null)


        while (cur!!.moveToNext()) {

            // Get the field values
            val eventID = cur.getLong(PROJECTION_ID_INDEX)
            val beginVal = cur.getLong(PROJECTION_BEGIN_INDEX)
            var title = cur.getString(PROJECTION_TITLE_INDEX)
            val organizer = cur.getString(PROJECTION_ORGANIZER_INDEX)

            // Do something with the values.
            Log.i("Calendar", "Event:  $title")
            val calendar = Calendar.getInstance()
            val curCalendar = Calendar.getInstance()
            calendar.timeInMillis = beginVal
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.KOREA)
            Log.i("Calendar", "Date: " + formatter.format(calendar.time))
            var arrContent: List<String>?
            if (title.contains("<CONTENT>")) {
                title = title.replace("<CONTENT>", "")
                arrContent = title.split("<CR>")
            } else {
                arrContent = null
            }
            val diffSec: Long = (calendar.timeInMillis - curCalendar.timeInMillis) / 1000       //초
            var diffMin: Long = diffSec / 60
            var resHour = 0L
            var resDay = 0L
            if (diffMin > 60) {
                resHour = (diffMin / 60)
                diffMin %= 60
            }
            if (resHour > 24) {
                resDay = resHour / 24
                resHour %= 24
            }

            Log.d("TAG", "Event ID: $eventID\nEvent: $title\nOrganizer: $organizer\nDate: ${formatter.format(calendar.time)}")
            if (calendar.get(Calendar.YEAR) == year && (calendar.get(Calendar.MONTH) + 1) == month && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                var timeRemain = ""
                if (resDay != 0L)
                    timeRemain += "0일"
                if (resHour != 0L)
                    timeRemain += " 20시간"
                timeRemain += " 5분전 "
                if (arrContent != null) {
                    mItems.add(PromiseItem(arrContent[0], arrContent[1], timeRemain))
                }
            }
        }
        //calendarView.addDecorator(EventDecorator(Color.RED, dates, this))
        adapter!!.notifyDataSetChanged()
    }
}