/*
 * Copyright 2018 Elliot Tormey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ie.elliot.x.labs.watertracker.ui.history

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.elliot.x.labs.watertracker.R
import ie.elliot.x.labs.watertracker.extension.toStyledPercentageString
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory
import ie.elliot.x.labs.watertracker.room.dao.totalConsumed
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_history.*
import org.threeten.bp.format.DateTimeFormatter

class HistoryRecyclerAdapter(private val data: List<List<IntakeHistory>>) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
      ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false))

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(intakeHistory: List<IntakeHistory>) {
      val intakeGoal = 1500
      val intakeOnDay = intakeHistory.totalConsumed()

      intakeHistory.first().dateTime.let { date ->
        tvDate.text = containerView.context.getString(R.string.history_date,
            date.format(DateTimeFormatter.ofPattern("EEEE")),
            date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
      }
      tvIntakePercentage.text = (intakeOnDay / intakeGoal.toFloat()).toStyledPercentageString()
      tvIntake.text = containerView.context.getString(R.string.history_intake, intakeOnDay, intakeGoal)

      rvHistoryDetail.apply {
        adapter = HistoryDetailRecyclerAdapter(intakeHistory)
        layoutManager = LinearLayoutManager(context)

        if (itemDecorationCount == 0) {
          addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
              super.getItemOffsets(outRect, view, parent, state)
              val padding = view.resources.getDimension(R.dimen.vertical_margin_half).toInt()
              outRect.bottom = padding

              if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = padding
              }
            }
          })
        }
      }
    }
  }
}