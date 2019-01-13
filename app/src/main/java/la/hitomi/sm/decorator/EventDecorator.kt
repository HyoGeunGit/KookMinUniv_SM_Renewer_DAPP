package la.hitomi.sm.decorator

import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay
import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import la.hitomi.sm.R


class EventDecorator(private val color: Int, dates: Collection<CalendarDay>, context: Activity) : DayViewDecorator {

    private val drawable: Drawable
    private val dates: HashSet<CalendarDay>

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
        this.dates = HashSet(dates)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        //view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
}
