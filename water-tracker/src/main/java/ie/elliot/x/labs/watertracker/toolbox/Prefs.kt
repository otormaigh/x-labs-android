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

package ie.elliot.x.labs.watertracker.toolbox

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ie.elliot.x.labs.watertracker.BuildConfig

object Prefs {
  const val HAS_USER_IN_ROOM = "prefs.has_user_in_room"
}

val Context.prefs: SharedPreferences get() = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

var SharedPreferences.hasUserInRoom: Boolean
  get() = getBoolean(Prefs.HAS_USER_IN_ROOM, false)
  set(value) = edit { putBoolean(Prefs.HAS_USER_IN_ROOM, value) }