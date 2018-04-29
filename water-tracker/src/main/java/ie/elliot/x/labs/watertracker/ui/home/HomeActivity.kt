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

package ie.elliot.x.labs.watertracker.ui.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import ie.elliot.x.labs.watertracker.BuildConfig
import ie.elliot.x.labs.watertracker.R
import ie.elliot.x.labs.watertracker.app
import ie.elliot.x.labs.watertracker.extension.formatDate
import ie.elliot.x.labs.watertracker.extension.toStyledPercentageString
import ie.elliot.x.labs.watertracker.extension.viewModelProvider
import ie.elliot.x.labs.watertracker.ui.history.HistoryActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class HomeActivity : AppCompatActivity(), HomeView {
  private val viewModel by viewModelProvider { HomeViewModel(this, app.database) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    setSupportActionBar(tbMain as Toolbar)
    viewModel.onCreate()

    tvDate.text = System.currentTimeMillis().formatDate("EEE, dd MMM yyyy")

    ivMenu.setOnClickListener { navDrawer.openDrawer(GravityCompat.END) }
    ivHistory.setOnClickListener { HistoryActivity.launch(this@HomeActivity) }
    ivWater.setOnClickListener {
      intakeProgressWheel.animateIn()
    }

    tvVersionName.text = getString(R.string.main_nav_version_name, BuildConfig.VERSION_NAME)
  }

  override fun setDailyIntake(currentIntake: Int, intakeGoal: Int) {
    tvTodaysIntake.text = "$currentIntake / ${intakeGoal}ml"
    tvDailyGoalValue.text = "${intakeGoal}ml"
  }

  override fun setIntakePercentage(intake: Float) {
    pbIntake.progress = intake

    tvIntakePercentage.text = intake.toStyledPercentageString()
  }
}
