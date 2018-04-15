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

package ie.elliot.x.labs.watertracker.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.CLASS_NAME
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.DATE
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.ID

@Entity(tableName = CLASS_NAME)
data class IntakeHistory(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val date: Long,
    val intakePercentage: Float,
    val intakeGoal: Int
) {
  object Key {
    const val CLASS_NAME = "intake_History"
    const val ID = "uid"
    const val DATE = "date"
  }
}

@Dao
abstract class IntakeHistoryDao : BaseDao<IntakeHistory> {
  @Query("DELETE from $CLASS_NAME")
  abstract fun deleteAll()

  @Query("SELECT * from $CLASS_NAME")
  abstract fun getAll(): List<IntakeHistory>

  @Query("SELECT * from $CLASS_NAME where $ID = :id")
  abstract fun get(id: String): IntakeHistory

  @Query("SELECT * from $CLASS_NAME where $DATE > :date")
  abstract fun getSinceDate(date: Long): List<IntakeHistory>
}