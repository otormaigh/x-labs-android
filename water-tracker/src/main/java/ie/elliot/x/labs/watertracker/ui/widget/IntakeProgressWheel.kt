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

package ie.elliot.x.labs.watertracker.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ie.elliot.x.labs.watertracker.R

class IntakeProgressWheel : View {
  private val paint by lazy {
    Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = ContextCompat.getColor(context, R.color.colorAccent)
    }
  }
  private val path by lazy { Path() }
  private var isVisible = false

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    path.apply {
      moveTo(0f, measuredHeight.toFloat())
      lineTo(measuredWidth / 2f, 0f)
      lineTo(measuredWidth.toFloat(), measuredHeight.toFloat())
      close()
    }

    canvas.drawPath(path, paint)
  }

  fun animateIn() {
    if (isVisible) {
      animate().translationY(measuredHeight.toFloat())
    } else {
      animate().translationY(0f)
    }

    isVisible = !isVisible
  }
}