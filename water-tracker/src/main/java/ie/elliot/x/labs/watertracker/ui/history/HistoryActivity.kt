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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import ie.elliot.x.labs.watertracker.R
import ie.elliot.x.labs.watertracker.app
import ie.elliot.x.labs.watertracker.extension.formatDate
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.toolbar_intake_history.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber

class HistoryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_history)
    setSupportActionBar(tbHistory as Toolbar)

    ivClose.setOnClickListener { onBackPressed() }
    val dateString = System.currentTimeMillis().formatDate("MMM, yyyy")
    tvDateSummary.text = "$dateString - 2,326L" // FIXME: test data

    launch {
      rvHistory.apply {
        val data = mutableListOf<List<IntakeHistory>>()
        val dates = app.database.history().getDates()
        dates.forEach { date ->
          val history = app.database.history().getOnDate(date)
          data.add(history)
        }

        withContext(UI) {
          adapter = HistoryRecyclerAdapter(data)
          layoutManager = LinearLayoutManager(this@HistoryActivity, RecyclerView.HORIZONTAL, false)
          addItemDecoration(DividerItemDecoration(this@HistoryActivity, DividerItemDecoration.HORIZONTAL))
        }
      }
    }
  }

  companion object {
    fun launch(activity: AppCompatActivity) {
      activity.startActivity(Intent(activity, HistoryActivity::class.java))
    }
  }
}