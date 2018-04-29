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

package ie.elliot.x.labs.watertracker.room.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime
import org.threeten.bp.format.DateTimeFormatter

object DateTimeConverter {
  private val formatterDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME
  private val formatterDate = DateTimeFormatter.ISO_LOCAL_DATE
  private val formatterTime = DateTimeFormatter.ISO_OFFSET_TIME

  @TypeConverter
  @JvmStatic
  fun toOffsetDateTime(value: String?): OffsetDateTime? = value?.let {
    return formatterDateTime.parse(value, OffsetDateTime::from)
  }

  @TypeConverter
  @JvmStatic
  fun fromOffsetDateTime(date: OffsetDateTime?) = date?.format(formatterDateTime)

  @TypeConverter
  @JvmStatic
  fun toOffsetLocalDate(value: String?): LocalDate? = value?.let {
    return formatterDate.parse(value, LocalDate::from)
  }

  @TypeConverter
  @JvmStatic
  fun fromLocalDate(date: LocalDate?) = date?.format(formatterDate)

  @TypeConverter
  @JvmStatic
  fun toOffsetOffsetTime(value: String?): OffsetTime? = value?.let {
    return formatterTime.parse(value, OffsetTime::from)
  }

  @TypeConverter
  @JvmStatic
  fun fromOffsetTime(date: OffsetTime?) = date?.format(formatterTime)
}