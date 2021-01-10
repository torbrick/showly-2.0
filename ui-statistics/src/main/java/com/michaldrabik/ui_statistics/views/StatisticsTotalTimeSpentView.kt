package com.michaldrabik.ui_statistics.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.google.android.material.card.MaterialCardView
import com.michaldrabik.ui_base.utilities.extensions.colorFromAttr
import com.michaldrabik.ui_statistics.R
import kotlinx.android.synthetic.main.view_statistics_card_total_time.view.*
import java.text.NumberFormat
import java.util.Locale.ENGLISH
import java.util.concurrent.TimeUnit

@SuppressLint("SetTextI18n")
class StatisticsTotalTimeSpentView : MaterialCardView {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    inflate(context, R.layout.view_statistics_card_total_time, this)
    layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
    setCardBackgroundColor(context.colorFromAttr(R.attr.colorCardBackground))
  }

  fun bind(timeMinutes: Long) {
    val formatter = NumberFormat.getNumberInstance(ENGLISH)

    val hours = TimeUnit.MINUTES.toHours(timeMinutes)
    val days = TimeUnit.HOURS.toDays(hours)

    viewTotalTimeSpentHoursValue.text = context.getString(R.string.textStatisticsTotalTimeSpentHours, formatter.format(hours))
    viewTotalTimeSpentMinutesValue.text = context.getString(R.string.textStatisticsTotalTimeSpentMinutes, formatter.format(timeMinutes))
    viewTotalTimeSpentSubValue.text = context.getString(R.string.textStatisticsTotalTimeSpentDays, formatter.format(days))
  }
}
