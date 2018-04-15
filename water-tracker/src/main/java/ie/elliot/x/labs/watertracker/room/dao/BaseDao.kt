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

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<T> {

  /**
   * Insert a single object into the Room.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(obj: T)

  /**
   * Insert an [ArrayList] of objects into the Room.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(obj: ArrayList<T>)

  /**
   * Update an already saved Room object.
   */
  @Update
  fun update(obj: T)

  /**
   * Delete an already saved Room object.
   */
  @Delete
  fun delete(obj: T)
}