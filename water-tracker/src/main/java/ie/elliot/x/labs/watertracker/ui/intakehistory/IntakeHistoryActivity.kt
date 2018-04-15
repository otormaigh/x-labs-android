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

package ie.elliot.x.labs.watertracker.ui.intakehistory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import ie.elliot.x.labs.watertracker.R
import kotlinx.android.synthetic.main.activity_intake_history.*
import kotlinx.android.synthetic.main.toolbar_intake_history.*

class IntakeHistoryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_intake_history)
    setSupportActionBar(tbHistory as Toolbar)

    ivClose.setOnClickListener { onBackPressed() }

    rvHistory.apply {
      adapter = IntakeHistoryRecyclerAdapter()
      layoutManager = LinearLayoutManager(this@IntakeHistoryActivity, RecyclerView.HORIZONTAL, false)
      addItemDecoration(DividerItemDecoration(this@IntakeHistoryActivity, DividerItemDecoration.HORIZONTAL))
    }
  }

  companion object {
    fun launch(activity: AppCompatActivity) {
      activity.startActivity(Intent(activity, IntakeHistoryActivity::class.java))
    }
  }
}