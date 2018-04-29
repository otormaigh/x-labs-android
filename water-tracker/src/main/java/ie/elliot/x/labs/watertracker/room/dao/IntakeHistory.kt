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

import android.arch.persistence.room.*
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.CLASS_NAME
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.DATE
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.DATE_TIME
import ie.elliot.x.labs.watertracker.room.dao.IntakeHistory.Key.ID
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime

@Entity(tableName = CLASS_NAME)
data class IntakeHistory(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = DATE_TIME)
    val dateTime: OffsetDateTime = OffsetDateTime.now(),
    val date: LocalDate = dateTime.toLocalDate(),
    val time: OffsetTime = dateTime.toOffsetTime(),
    val consumed: Int
) {
  object Key {
    const val CLASS_NAME = "intake_History"
    const val ID = "uid"
    const val DATE_TIME = "date_time"
    const val DATE = "date"
    const val TIME = "time"
  }
}

@Dao
abstract class IntakeHistoryDao : BaseDao<IntakeHistory> {
  @Query("DELETE from $CLASS_NAME")
  abstract fun deleteAll()

  @Query("SELECT * from $CLASS_NAME")
  abstract fun getAll(): List<IntakeHistory>

  @Query("SELECT * from $CLASS_NAME where $ID = :id")
  abstract fun get(id: Int): IntakeHistory

  @Query("SELECT * from $CLASS_NAME where $DATE=:localDate ORDER BY $DATE_TIME DESC")
  abstract fun getOnDate(localDate: LocalDate): List<IntakeHistory>

  @Query("SELECT $DATE from $CLASS_NAME GROUP BY $DATE ORDER BY $DATE DESC")
  abstract fun getDates(): List<LocalDate>
}

fun List<IntakeHistory>.totalConsumed() = sumBy { it.consumed }