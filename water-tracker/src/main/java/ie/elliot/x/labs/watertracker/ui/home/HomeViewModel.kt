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

import android.arch.lifecycle.ViewModel
import ie.elliot.x.labs.watertracker.room.WaterTrackerDatabase
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class HomeViewModel(private val view: HomeView, private val database: WaterTrackerDatabase) : ViewModel() {

  fun onCreate() {
    launch {
      val user = database.user().get()
      withContext(UI) {
        view.setDailyIntake(0, user.dailyIntakeGoal)
      }
    }
  }
}