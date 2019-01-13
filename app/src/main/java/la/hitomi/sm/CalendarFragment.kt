package la.hitomi.sm

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import la.hitomi.sm.decorator.EventDecorator
import la.hitomi.sm.decorator.SaturdayDecorator
import la.hitomi.sm.decorator.SundayDecorator
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
         

        view.calendarView.state().edit().setMinimumDate(CalendarDay.from(2018, 8, 5))
        view.calendarView.addDecorators(
                SundayDecorator(),
                SaturdayDecorator())

        view.calendarView.setOnDateChangedListener { _, date, _ ->
            activity.startActivity<DetailProActivity>("year" to date.year, "month" to date.month, "day" to date.day)
        }

        addEvent("늦지 말기", "국민대학교 oss 개발자 캠프", 2019, 1, 12)
        addEvent("중요한 일정", "에드캔 시연회", 2019, 1, 11)

        readEvents(view)
        return view
    }
    fun readEvents(view: View) {
        val instanceProjection = arrayOf(CalendarContract.Instances.EVENT_ID, // 0
                CalendarContract.Instances.BEGIN, // 1
                CalendarContract.Instances.TITLE, // 2
                CalendarContract.Instances.ORGANIZER)

        // The indices for the projection array above.
        val projectionIdIndex = 0
        val projectionBeginIndex = 1
        val projectionTitleIndex = 2
        val projectionOrganizerIndex = 3

        // Specify the date range you want to search for recurring event instances
        val beginTime = Calendar.getInstance()
        //beginTime.set(2017, 9, 23, 8, 0)
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime.set(2019, 12, 30, 8, 0)
        val endMillis = endTime.timeInMillis

        addEvent("늦지 말기", "국민대학교 oss 개발자 캠프", 2019, 1, 12)
        addEvent("중요한 일정", "에드캔 시연회", 2019, 1, 11)

        // Construct the query with the desired date range.
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)

        // Submit the query
        val cur = activity.contentResolver.query(builder.build(), instanceProjection, null, null, null)

        val dates = ArrayList<CalendarDay>()

        while (cur!!.moveToNext()) {

            // Get the field values
            val eventID = cur.getLong(projectionIdIndex)
            val beginVal = cur.getLong(projectionBeginIndex)
            val title = cur.getString(projectionTitleIndex)
            val organizer = cur.getString(projectionOrganizerIndex)

            // Do something with the values.
            Log.i("Calendar", "Event:  $title")
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = beginVal
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.KOREA)
            Log.i("Calendar", "Date: " + formatter.format(calendar.time))
            addEvent("늦지 말기", "국민대학교 oss 개발자 캠프", 2019, 1, 12)
            addEvent("중요한 일정", "에드캔 시연회", 2019, 1, 11)


            d("TAG","Event ID: $eventID\nEvent: $title\nOrganizer: $organizer\nDate: ${formatter.format(calendar.time)}")
            if(title.contains("<CONTENT>"))
            dates.add(CalendarDay.from(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)))
        }
        view.calendarView.addDecorator(EventDecorator(Color.RED, dates, activity))
    }
    fun addEvent(title: String, location: String, year:Int, month:Int,day:Int) {
        val eventTitle = "<CONTENT>$title<CR>$location"
        /*if (isEventAlreadyExist(eventTitle)) {
            Snackbar.make(view, "Jazzercise event already exist!", Snackbar.LENGTH_SHORT).show()
            return
        }*/

        val calID: Long = 3
        val startMillis: Long
        val endMillis: Long
        val beginTime = Calendar.getInstance()
        beginTime.set(year, month-1, day)
        startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime.set(year, month-1, day)
        endMillis = endTime.timeInMillis

        val cr = activity.contentResolver
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, startMillis)
        values.put(CalendarContract.Events.DTEND, endMillis)
        values.put(CalendarContract.Events.TITLE, eventTitle)
        values.put(CalendarContract.Events.DESCRIPTION, "PR Calendar")
        values.put(CalendarContract.Events.CALENDAR_ID, calID)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        values.put(CalendarContract.Events.ORGANIZER, "highton4.hitomila@gmail.com")

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
            val eventID = java.lang.Long.parseLong(uri!!.lastPathSegment)
            Log.i("Calendar", "Event Created, the event id is: $eventID")
            //Snackbar.make(view, "Jazzercise event added!", Snackbar.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_CALENDAR), 1001)
        }
    }
}
