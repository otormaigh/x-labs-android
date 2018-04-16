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

package ie.elliot.x.labs.watertracker

import android.arch.persistence.room.Room
import android.content.Context
import ie.elliot.x.labs.lib.common.TimberApplication
import ie.elliot.x.labs.watertracker.extension.random
import ie.elliot.x.labs.watertracker.room.WaterTrackerDatabase
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory
import ie.elliot.x.labs.watertracker.room.dao.User
import ie.elliot.x.labs.watertracker.toolbox.hasUserInRoom
import ie.elliot.x.labs.watertracker.toolbox.prefs
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.concurrent.TimeUnit

class WaterTrackerApplication : TimberApplication() {
  val database by lazy {
    Room.databaseBuilder(applicationContext, WaterTrackerDatabase::class.java, "water-tracker-db")
        .fallbackToDestructiveMigration()
        .build()
  }

  override fun onCreate() {
    super.onCreate()

    initTimber(BuildConfig.DEBUG)
    initRoom()
  }

  private fun initRoom() {
    if (!prefs.hasUserInRoom) {
      launch {
        database.user().insert(User(dailyIntakeGoal = 1500))

        for (i in 0 until 10) {
          val dateTime = System.currentTimeMillis() - (i * TimeUnit.DAYS.toMillis(1))

          for (j in 0 until 20) {
            database.history().insert(
                IntakeHistory(
                    dateTime = dateTime,
                    consumed = (10..150).random().toLong())
            )
          }
        }

        withContext(UI) {
          prefs.hasUserInRoom = true
        }
      }
    }
  }
}


val Context.app: WaterTrackerApplication
  @Suppress("detekt.UnsafeCast")
  get() = applicationContext as WaterTrackerApplication