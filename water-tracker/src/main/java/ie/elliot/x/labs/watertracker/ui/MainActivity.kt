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

package ie.elliot.x.labs.watertracker.ui

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import ie.elliot.x.labs.watertracker.BuildConfig
import ie.elliot.x.labs.watertracker.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_main.*
import kotlinx.android.synthetic.main.toolbar_main.*


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(tbMain as Toolbar)

    setIntakePercentage(0.3f)

    ivMenu.setOnClickListener { navDrawer.openDrawer(GravityCompat.END) }

    tvVersionName.text = getString(R.string.main_nav_version_name, BuildConfig.VERSION_NAME)
  }

  private fun setIntakePercentage(intake: Float) {
    pbIntake.progress = intake

    val source = "${intake.toInt()}%"
    tvIntakePercentage.text = SpannableString(source).apply {
      setSpan(RelativeSizeSpan(2f), 0, source.length - 1, 0)
    }
  }
}
