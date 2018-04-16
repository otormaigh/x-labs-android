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

package ie.elliot.x.labs.watertracker.extension

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import java.text.DecimalFormat


/**
 * Expected input in the format:
 * 0.1 = 10%
 * 0.2 = 20%
 * ...
 * 1.0 = 100%
 */
fun Float.toStyledPercentageString(): SpannableString {
  val source = DecimalFormat("0%").format(this)
  return SpannableString(source).apply {
    setSpan(RelativeSizeSpan(2f), 0, source.length - 1, 0)
  }
}